package com.benpinkas.helloben.leCasseBrique;

import android.graphics.PointF;

import com.benpinkas.bEngine.object.BcontainerObject;

import com.benpinkas.bEngine.Action;


public class BricksGroup extends BcontainerObject {

    private Brick[] bricks;
    public CrushPlayer crushPlayer = new CrushPlayer(this);


    public BricksGroup (float posX, float posY, PointF[] relativePos, PointF[] size) {
        super(posX, posY, 0, 0);

        bricks = new Brick[relativePos.length];

        for(int i=0; i<relativePos.length;i++) {
            bricks[i] = new Brick(0, 0, size[i].x, size[i].y);

            addChild(bricks[i], relativePos[i].x, relativePos[i].y);
        }
/*
        bricks[0].setTouchU(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                crush();
                System.out.println("touch!!");
                return null;
            }
        });
*/
    }

    @Override
    public void update(float dt) {
    }

    //TODO move to Bobject or whatever class implements setPos
    public boolean tendToPos(float x, float y, float friction){
        float currX = this.getPosX();
        float currY = this.getPosY();
        float dx = (x - currX) / friction;
        float dy = (y - currY) / friction;

        if (Math.abs(dx) >  0.0001 || Math.abs(dy) > 0.0001){
            setPos(currX + dx, currY + dy);
            return true;
        } else {
            setPos(x, y);
            return false;
        }
    }

    public Brick[] getBricks(){
        return bricks;
    }


    public static class CrushPlayer extends Action {

        private BricksGroup bricks;
        private int ACTION_T = 200;
        private enum State  { FALL, LATERAL, COMEBACK }
        private State state = State.FALL;
        private Action otherAction;

        private float posXinit, posYinit = 0;
        private float destX;

        public CrushPlayer(BricksGroup o){
            bricks = o;
            period = ACTION_T;
        }

        // TODO
        // need the bat position
        // velx and vely initial (when the crushing begins) are calculated
        // according to the distance between the group of bricks (supposed to look like a fist)
        // and the bat

        public void setACTION_T (int action_t) {
            ACTION_T = action_t;
            period = action_t;
        }

        public void setOtherAction (Action action) {
            otherAction = action;
        }

        @Override
        public void init() {
            state = State.FALL;
            posXinit = bricks.getPosX();
            posYinit = bricks.getPosY();
        }

        @Override
        public void desInit() {
            if (otherAction != null){
                otherAction.start();
            }
        }

        //TODO avoir une action, "go back to initial position" execute apres celle la,
        // cette action retourne juste lentement a la position d'origine.

        @Override
        public boolean update(float dt) {
            switch (state) {
                case FALL:

                   if( bricks.tendToPos(bricks.getPosX(), 0.9f, 7) ){
                       return true;
                   } else {
                       if (bricks.getPosX() > 0.5f) {
                           destX = 0.1f;
                       } else {
                           destX = 0.9f;
                       }
                       state = State.LATERAL;
                   }

                case LATERAL:

                    if( bricks.tendToPos(destX, bricks.getPosY(), 6) ){
                       return true;
                    } else {
                       state = State.COMEBACK;
                    }
                case COMEBACK:
                   return bricks.tendToPos(posXinit, posYinit, 13);
            }
            return  true;
        }
    }

}
