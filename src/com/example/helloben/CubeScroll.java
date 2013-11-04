package com.example.helloben;

import com.example.BGL.object.Brectangle;

/**
 * Created by Ben on 10/28/13.
 */
public class CubeScroll extends Brectangle {
    @Override
    public void touchDown() {
        super.touchDown();
        System.out.println("TOUCHED");

    }

    @Override
    public void touchUp() {
        super.touchUp();
    }

    @Override
    public void touchMove(float x, float y) {
        super.touchMove(x, y);
        pos.x = x - anchor.x;
        pos.y = y - anchor.y;
        System.out.println( x + "    plplplpl   " + y  );
    }

    @Override
    public void touchUpMove(float x, float y) {
        super.touchUpMove(x, y);
    }

    public CubeScroll( float x, float y, float w, float h, float r, float g, float b ){
        super(  x,  y,  w,  h,  r, g,  b );


    }
}

