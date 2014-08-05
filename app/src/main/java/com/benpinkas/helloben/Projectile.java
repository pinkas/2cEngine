package com.benpinkas.helloben;

import android.graphics.PointF;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.CollisionService;

/**
 * Created by Ben on 3/13/14.
 */
public class Projectile extends Brectangle {


    protected PointF vel;
    private float X;
    private float Y;
    private int lifeTime = 100;
    private int progress = 0;

    protected int totalHp = 10;
    protected int currentHp = 10;
    private boolean active = false;


    public Projectile( float x, float y,  float velx, float vely ){
        super(x, y, 0.02f, 0.03f, 0.9f, 0.1f, 0.5f);
        setVisible(false);
        vel = new PointF( velx, vely );
    }

    public Projectile( float velx, float vely ){
        super(0, 0, 0.02f, 0.03f, 0.9f, 0.1f, 0.5f);
        setVisible(false);
        X = velx;
        Y = vely;
        vel = new PointF(0, 0);
    }

    public void setLifetime ( int lifeTime){
        this.lifeTime = lifeTime;
    }

    public void shoot(){
        active = true;
        currentHp = totalHp;
        vel.x = X;
        vel.y = Y;
        setVisible(true);
    }

    public boolean isActive(){
        return active;
    }


    public void turnOff(){
        setVisible(false);
        progress = 0;
        vel.x = 0;
        vel.y = 0;
        active = false;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (isActive()) {
/*
            pos.x = pos.x + vel.x*dt;
            pos.y = pos.y + vel.y*dt;
*/
            progress++;
            if (progress > this.lifeTime) {
                // FIXME SHould that be in every implementation of projectile???
                turnOff();
            }
        }

    }

    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        currentHp--;
        if( currentHp <= 0){
            turnOff();
        }
    }

    public int getHp() {
        return currentHp;
    }

    public void setHp(int hp) {
        totalHp = hp;
        currentHp = totalHp;
    }
}
