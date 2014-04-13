package com.example.helloben;

import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 10-Apr-14.
 */
public class SpaceShipEnemy extends SpaceShip {

    private final Attack attack;
    private final Attack attack2;
    private final Attack attack3;

    private float stepY = 0.003f;


    public SpaceShipEnemy( Scene scene ){
        super(0.8f, 0, 0.2f, 0.27f, 0.8f, 0.8f, 0.5f, scene);

        float traj[] = {
            -0.01f, -0.005f,
            -0.01f, 0,
            -0.01f, 0.005f,};
        /*Create attack and add the projectils to the scene*/
        attack = new Attack( this, traj,-0.1f,0.5f, 160 );
        for (Projectile proj : attack.getProjList() ) {
            scene.addAsync(proj);
        }

        float traj2[] = {
            -0.01f, 0.01f,
            0.01f, 0.01f,
            0, 0.01f};

        attack2 = new Attack( this, traj2, 0.5f, 1f, 200 );
        for (Projectile proj2 : attack2.getProjList() ) {
            scene.addAsync(proj2);
        }
        float traj3[] = {0.0001f, 0};
        attack3 = new AttackLaser( this, traj3, 0.5f, 1f, 130, 0.04f, 1, 8 );
        for (Projectile proj3 : attack3.getProjList() ) {
            scene.addAsync(proj3);
        }
    }

    @Override
    public void update() {
        super.update();

        restaureColor();

        pos.x = pos.x + stepY;

        if (pos.x + size.x > 1.0f  || pos.x < 0 ){
            stepY = - stepY;
        }


        /*TODO the ship has a list of attack and here we would execute all the timer of all the attack
          by doing so you wouldnt spend 15 minutes wondering why this new attack you implemented does
          not work!!!1!1!
         */
        attack.timer();
        attack2.timer();
        attack3.timer();
    }


    @Override
    public void collision(){
        setColor(1.0f,1.00f,1.0f,0f);
    }

    public void restaureColor(){
        setColor(0.8f,0.8f,0.5f,1.0f);
    }

}
