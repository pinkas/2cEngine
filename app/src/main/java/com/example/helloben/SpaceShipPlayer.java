package com.example.helloben;

import android.graphics.PointF;

import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 10-Apr-14.
 */
public class SpaceShipPlayer extends SpaceShip {

    private PointF vel = new PointF(0,0);

    public SpaceShipPlayer( Scene scene ){
        super(0, 0, 0.1f, 0.1f, 0.1f, 0.8f, 0.5f, scene);
    }

    @Override
    public void touchDown() {
        super.touchDown();
        shoot( pos.x + size.x, pos.y + size.y/2, 0.01f, 0);
    }

       @Override
    public void update() {
        super.update();
        //System.out.println( vel.x + "   " + this + "   " + vel.y );
        calcYaw();
        setColor(0.1f,0.8f,0.5f,1f);
    }



    public void calcYaw (){
        angle.x = vel.y * 1500;
        angle.y = vel.x * 1500;

    }

    public PointF getVel() {
        return vel;
    }

    public void setVel(float velX, float velY) {
        this.vel.x = velX;
        this.vel.y = velY;
    }

    @Override
    public void collision(){
        setColor(1.0f,1.00f,1.0f,0f);
    }

}
