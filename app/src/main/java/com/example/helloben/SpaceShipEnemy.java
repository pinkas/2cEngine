package com.example.helloben;

import android.widget.Space;

import com.example.bEngine.object.SpriteSheet;
import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 10-Apr-14.
 */
public class SpaceShipEnemy extends SpaceShip {

    private final Phase[] phase = new Phase[3];
    private int currentPhase = 0;

    //TODO - juste pour but d'illustrer un changement de phase
    private int life;


    private int[][] phaseLogic;
    int phaseExitMode;
    int pastPhase;


    public SpaceShipEnemy( SpriteSheet[] sheet, Scene scene ){
        super(0.8f, 0, 0.2f, 0.27f, sheet, scene);

        life = 100;

        phase[0] = new phase1(this);
        phase[1] = new phase2(this);
        phase[2] = new phase3(this);

        /* Phase logic */
        phaseLogic = new int[3][];

        phaseLogic[0] = new int[2];
        phaseLogic[0][0] = 1;
        phaseLogic[0][1] = 2;

        phaseLogic[1] = new int[2];
        phaseLogic[1][0] = 0;
        phaseLogic[1][1] = 2;

    }

    @Override
    public void update(float dt) {
        super.update(dt);

        restaureColor();

        phase[currentPhase].update();

        phaseExitMode = phase[currentPhase].isDone();
        if ( phaseExitMode >= 0 ){
            pastPhase = currentPhase;
            currentPhase = phaseLogic[currentPhase][phaseExitMode];
            phase[currentPhase].applyNewPhase();
        }

        // evaluer condition de fin de phase
          // si fin de phase
          // attack devient un autre tableau d'attack (celui qui contient les attacks de la nouvelle phase
          // change l'algorythme de deplacement
          // change des caracteristiques (armure, vitesse ...)
          // change les aspects graphiques ( ou non )

    }


    @Override
    public void collision(){
        life --;
        //setColor(0.5f,1.00f,1.0f,0f);
    }

    public void restaureColor(){
        //setColor(0.8f,0.8f,0.5f,1.0f);
    }


    private class phase1 implements Phase{

        private SpaceShip actor;
        private Attack[] attack;
        private int progress;


        private int crazyAttackCpt;
        private Projectile[] crazyProjectiles = new Projectile[10];

        public phase1(SpaceShip actor ){
            this.actor = actor;

/*
            float traj2[] = {
                    -0.01f, 0.01f,
                    0.01f, 0.01f,
                    0, 0.01f};

            attack[0] = new Attack( actor, traj2, 0.5f, 1f );
            for (Projectile proj2 : attack[0].getProjList()) {
                scene.addAsync(proj2);
            }
*/

            for (int i=0; i<10; i++)
            {
                crazyProjectiles[i] = new Projectile(0, 0.0001f);
                crazyProjectiles[i].setLifetime(1000);
                crazyProjectiles[i].setHp(1);

                scene.addAsync(crazyProjectiles[i]);

            }





        }

        @Override
        public void update() {
            progress ++;
            move();

            if (progress%120 == 0) {

                float posx;
                if ( Math.random()  >= 0.5)
                    posx = 0.2f;
                else
                        posx = 0.8f;

                crazyProjectiles[crazyAttackCpt].setVisible(true);
                crazyProjectiles[crazyAttackCpt].setPos(posx, 0);
                crazyProjectiles[crazyAttackCpt].shoot();
                crazyAttackCpt ++;
            }
        }

        @Override
        public int isDone() {
            if( progress > 1000 ) {
                return 0;
            }
            else if ( life < 3 ) {
                return 1;
            }
            else {
                return -1;
            }
        }

        @Override
        public void applyNewPhase() {
            progress = 0;
            crazyAttackCpt = 0;
        }

        @Override
        public void move() {
        }

    }


    private class phase2 implements Phase{

        private SpaceShip actor;
        private Attack[] attack= new Attack[1];
        private int progress;
        private float stepY = 0.0033f;



        public phase2(SpaceShip actor ){
            this.actor = actor;

            float traj3[] = {0.0001f, 0};
            attack[0] = new AttackLaser( this.actor, traj3, 0.5f, 1f, 130, 0.04f, 1, 2 );
            for (Projectile proj3 : attack[0].getProjList()) {
                scene.addAsync(proj3);
            }
        }

        @Override
        public void update() {
            progress++;
            move();

            if (progress%50 == 0){
                attack[0].initProjectiles();
            }
        }

        public int isDone() {
            if( progress > 600 ) {
                return 0;
            }
            else if ( life < 2 ) {
                return 1;
            }
            else {
                return -1;
            }
        }

        @Override
        public void applyNewPhase() {
            progress = 0;
        }

        @Override
        public void move() {
            pos.x = pos.x + stepY;
            if (pos.x + size.x > 1.0f  || pos.x < 0 ){
                stepY = - stepY;
            }

        }

    }



    private class phase3 implements Phase{

        private SpaceShip actor;
        private Attack[] attack= new Attack[2];
        private int progress;
        private float stepY = 0.0063f;


        public phase3(SpaceShip actor ){
            this.actor = actor;


            float traj2[] = {
                    -0.01f, 0.01f,
                    -0.05f, 0.01f,
                    0.01f, 0.01f,
                    0.05f, 0.01f,
                    0, 0.01f};

            attack[0] = new Attack( actor, traj2, 0.5f, 1f );
            for (Projectile proj2 : attack[0].getProjList()) {
                scene.addAsync(proj2);
            }

            attack[1] = new Attack( actor, traj2, 0.5f, 1f );
            for (Projectile proj2 : attack[1].getProjList()) {
                scene.addAsync(proj2);
            }


        }

        @Override
        public void update() {
            progress++;
            move();

            if (progress%80 == 0){
                attack[0].initProjectiles();
            }

           if (progress%100 == 0){
                attack[1].initProjectiles();
            }
        }

        public int isDone() {
            return -1;
        }

        @Override
        public void applyNewPhase() {
        }

        @Override
        public void move() {
            pos.x = pos.x + stepY;
            if (pos.x + size.x > 1.0f  || pos.x < 0 ){
                stepY = - stepY;
            }

        }

    }


}
