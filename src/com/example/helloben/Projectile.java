package com.example.helloben;

import android.graphics.PointF;

import com.example.bEngine.object.Brectangle;

/**
 * Created by Ben on 3/13/14.
 */
public class Projectile extends Brectangle {


    protected PointF vel;


    public Projectile(){
        super(0,0,0.03f,0.03f, 0.9f, 0.1f, 0.5f);
        vel = new PointF(0,0);
    }

    @Override
    public void update() {
        super.update();
        this.pos.x = this.pos.x + vel.x;
        this.pos.y = this.pos.y + vel.y;
    }
}
