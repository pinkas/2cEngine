package com.example.BGL;

import android.content.Context;
import android.graphics.Point;

/**
 * Created by Ben on 7/11/13.
 */
public class Marble extends BglObject {

    private int cpt = 0;

    Marble( int x, int y, int w, int h, Context context, int texture_id  ){
        super( x, y, w, h, context, texture_id );
        this.anchorPointSet(0.5f,0.5f);
    }

    @Override
    public void update() {
        //update speed
        speed.x = (int) (speed.x - speed.x*0.1);
        speed.y = (int) (speed.y - speed.y*0.1);
        //update pos
        pos.x = pos.x + speed.x;
        pos.y = pos.y + speed.y;
        super.update();
    }

    @Override
    public void touchDown() {
        pressed = true;
    }

    @Override
    public void touchMove() {
    }

    @Override
    public void touchDownMove(int px, int py){
        speed.x = (pos.x - px)/10;
        speed.y = (pos.y - py)/10;

    }

    public void touchUp(){
        System.out.println( "PRESSED FALSE" );
        pressed = false;

    }

}
