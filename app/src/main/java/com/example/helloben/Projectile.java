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

    public void shoot( ){
        vel.x = X;
        vel.y = Y;
    }

    @Override
    public void update() {
        super.update();
        this.pos.x = this.pos.x + vel.x;
        this.pos.y = this.pos.y + vel.y;

      //  this.angle.x = this.angle.x + 1;
    //    this.angle.y = this.angle.y + 1;
        this.angle.z = this.angle.z + 3;

    }
}
