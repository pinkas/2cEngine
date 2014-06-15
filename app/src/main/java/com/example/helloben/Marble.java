package com.example.helloben;

import com.example.bEngine.object.BglSprite;

/**
 * Created by Ben on 7/11/13.
 */
public class Marble extends BglSprite {

    private int cpt = 0;

    public Marble(int x, int y, int w, int h, int[] texture_id){
        super( x, y, w, h, texture_id );
        this.anchorPointSet(0.5f,0.5f);
    }

    @Override
    public void update(float dt) {
        //update speed
        //speed.x = (int) (speed.x - speed.x*0.1);
        //speed.y = (int) (speed.y - speed.y*0.1);
        //update pos
        //pos.x = pos.x + speed.x;
        //pos.y = pos.y + speed.y;
        super.update(dt);
    }

    @Override
    public void touchDown() {
        super.touchDown();
    }

    @Override
    public void touchMove(float x, float y) {
        super.touchMove(x, y);
    }

    @Override
    public void touchUpMove(float px, float py){
        super.touchUpMove(px,py);
        //speed.x = (pos.x - px)/6;
        //speed.y = (pos.y - py)/6;
    }

    public void touchUp(){
      //  pressed = false;
    }

}
