package com.benpinkas.helloben.leCasseBrique.wizard;

import android.graphics.PointF;

import com.benpinkas.bEngine.effect.Explosion;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.helloben.leCasseBrique.Ball;

public class Projectile extends BglSprite {

    private final int DEFAULT_SPIN_SPEED = 10;
    private int spinSpeed = DEFAULT_SPIN_SPEED;
    private final float FRICTION = 1.03f;
    private Explosion explosion;

    public Projectile(float x, float y, float w, float h, int[] res) {
        super(x, y, w, h, res);
        setSize(0.15f);
        explosion = new Explosion(this, 2, 2);
        addUpdatable(explosion);
        explosion.endCallback();
    }

    public void update(float dt){
        float posX = getPosX();
        float posY = getPosY();
        PointF vel = getVel();

        vel.x = vel.x/FRICTION;

        setPos(posX + dt*vel.x, posY + dt*vel.y);
        setAngleZ( getAngleZ() + spinSpeed );
    }

    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        if ( collider instanceof Ball) {
            explode();
        }
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

    public void explode(){
        spinSpeed = 0;
        explosion.start();
        setCollide(false);
    }

    public void init(){
        spinSpeed = DEFAULT_SPIN_SPEED;
        explosion.endCallback();
        setCollide(true);
    }
}
