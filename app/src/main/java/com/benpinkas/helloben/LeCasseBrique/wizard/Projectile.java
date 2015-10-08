package com.benpinkas.helloben.LeCasseBrique.wizard;

import android.graphics.PointF;

import com.benpinkas.bEngine.object.BglSprite;

public class Projectile extends BglSprite {

    private final int SPIN_SPEED = 10;
    private final float FRICTION = 1.03f;

    public Projectile(float x, float y, float w, float h, int[] res) {
        super(x, y, w, h, res);
    }

    public void update(float dt){
        float posX = getPosX();
        float posY = getPosY();
        PointF vel = getVel();

        vel.x = vel.x/FRICTION;

        setPos(posX + dt*vel.x, posY + dt*vel.y);
        setAngleZ( getAngleZ() + SPIN_SPEED );
    }

    public void shoot(float velX, float velY){
        vel.x = velX;
        vel.y = velY;
        setVisible(true);
    }

    public void stop(){
        vel.x = 0;
        vel.y = 0;
        setVisible(false);
    }


}
