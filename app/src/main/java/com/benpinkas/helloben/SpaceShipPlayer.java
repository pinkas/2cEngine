package com.benpinkas.helloben;

import android.graphics.PointF;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.service.CollisionService;

/**
 * Created by Ben on 10-Apr-14.
 */
public class SpaceShipPlayer extends SpaceShip {

    private PointF vel = new PointF(0,0);
    private SpaceShip enemyShip;
    private int reload = 0;
    private Attack mainAttack;


    public SpaceShipPlayer( SpriteSheet[] sheet, Scene scene ){
        super(0, 0, 0.16f, 0.2f, sheet, scene );

        float traj3[] = {0.0001f, 0};
        mainAttack = new AttackLaser( this, traj3, 0.35f, -5f, 130, 0.04f, 1, 2 );
        for (Projectile proj3 : mainAttack.getProjList()) {
            scene.addAsync(proj3);
        }

    }

    @Override
    public void touchDown() {
        super.touchDown();
        mainAttack.initProjectiles();
    }

       @Override
    public void update(float dt) {
        super.update(dt);
        calcYaw();

        // it used to inehrit from rectangle setColor(0.1f,0.8f,0.5f,1f);

        if ( isReachable(enemyShip , 0,-0.1f) && (reload == 0) ){
            //shoot( pos.x + size.x, pos.y + size.y/2, 0.0f, -0.01f);
            reload = 25;
        }
           startMinus();

    }

    public void startMinus(){
        if (reload > 0) {
            reload--;
        }
    }

    public void setEnemy(SpaceShip ship){
        this.enemyShip = ship;
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
    public void collision(Bobject collider, CollisionService.collisionSide cs){

        // it used to inerit from rectangle setColor(1.0f,1.00f,1.0f,0f);
    }

}
