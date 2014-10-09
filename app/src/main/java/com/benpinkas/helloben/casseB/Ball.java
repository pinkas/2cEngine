package com.benpinkas.helloben.casseB;

import android.graphics.PointF;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.CollisionService.collisionSide;
import com.benpinkas.bEngine.service.MessageManager;


/**
 * Created by Ben on 13-Jul-14.
 */
public class Ball extends Brectangle {

    private PointF prevPos = new PointF();
    private PointF rawVel = new PointF();

    private boolean collisionDuringThisFrame;

    private static float SPEED_FACTOR = 3.5f;

    public Ball(float x, float y, float w, float h, float r, float g, float b){
        super(x,y,w,h,r,g,b);
    }


    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        //pos = prevPos;
        if ( collider instanceof Bat && !collisionDuringThisFrame ) {
            float batCenterX = collider.getPosX(0.5f);
            float ballCenterX = this.getPosX(0.5f);

            rawVel.y = -rawVel.y;
            rawVel.x = (ballCenterX - batCenterX) * SPEED_FACTOR;

            collisionDuringThisFrame = true;
        }
        else if ( collider instanceof Brick || collider instanceof Brectangle){
            if(cs == collisionSide.LEFT || cs == collisionSide.RIGHT ){
                rawVel.x = -rawVel.x;
            }
            if (cs == collisionSide.BOTTOM || cs == collisionSide.TOP ){
                rawVel.y = -rawVel.y;
            }
        }
    }

    public void fire(float velX, float velY){
        rawVel.x = velX;
        rawVel.y = velY;
    }

    public float getRawVelX() {
        return rawVel.x;
    }

    public float getRawVelY() {
        return rawVel.y;
    }

    @Override
    public void update(float dt) {

        if (getPosY() > 0.95f ) {
            MessageManager.sendMessage("lost_ball");
        }

        collisionDuringThisFrame = false;
        //PointF pos = this.getPos();
        float posx = this.getPosX();
        float posy = this.getPosY();

        vel.x = rawVel.x*dt;
        vel.y = rawVel.y*dt;

        setPos (posx + vel.x, posy + vel.y);
    }
}
