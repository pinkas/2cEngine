package com.example.BGL;

import android.content.Context;

/**
 * Created by Ben on 7/11/13.
 */
public class Marble extends BglObject {

    private int cpt = 0;

    Marble( int x, int y, int w, int h, Context context, int texture_id  ){
        super( x, y, w, h, context, texture_id );
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
        cpt = 0;
    }

    @Override
    public void touchMove() {
        cpt ++;
    }

    public void touchUp(){
        speed.x = cpt;
    }

}
