package com.example.helloben;

import android.graphics.PointF;

import com.example.bEngine.object.Brectangle;

/**
 * Created by Ben on 3/13/14.
 */
public class Projectile extends Brectangle {


    protected PointF vel;
    private float X;
    private float Y;
    private int lifeTime = 100;
    private int life = 0;


    public Projectile( float x, float y,  float velx, float vely ){
        super(x, y, 0.02f, 0.03f, 0.9f, 0.1f, 0.5f);
        vel = new PointF( velx, vely );
    }

    public Projectile( float velx, float vely ){
        super(0, 0, 0.02f, 0.03f, 0.9f, 0.1f, 0.5f);
        X = velx;
        Y = vely;
        vel = new PointF(0, 0);
    }

    public void setLifetime ( int lifeTime){
        this.lifeTime = lifeTime;
    }

    public void shoot(){
        vel.x = X;
        vel.y = Y;
        setVisible(true);
    }

    public boolean isOn(){
        if (vel.x == 0f && vel.y == 0f)
            return false;
        else
            return true;
    }

    @Override
    public void update() {
        super.update();

        if (isOn()) {

            pos.x = pos.x + vel.x;
            pos.y = pos.y + vel.y;

            life++;
            if (life > this.lifeTime) {
                // FIXME SHould that be in every implementation of projectile???
                setVisible(false);
                life = 0;
                vel.x = 0;
                vel.y = 0;
            }
        }

    }
}
