package com.benpinkas.helloben.leCasseBrique;


import com.benpinkas.R;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.ObjectPool;
import com.benpinkas.bEngine.effect.Explosion;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.service.Bcall;
import com.benpinkas.bEngine.service.MessageManager;
import com.benpinkas.bEngine.Action;
import com.benpinkas.helloben.leCasseBrique.wizard.Projectile;
import com.benpinkas.helloben.leCasseBrique.wizard.WizAttack;
import com.benpinkas.helloben.leCasseBrique.wizard.WizCast;
import com.benpinkas.helloben.leCasseBrique.wizard.WizMove;
import com.benpinkas.helloben.leCasseBrique.wizard.Wizard;

import java.util.concurrent.Callable;


public class SceneForBall extends Scene {
    private final static int MAX_LIFE = 3;
    private final static int BONUS_DURATION = 300;

    private Ball myBall;
    private Brick[] destroyMe = new Brick[300];
    private Wizard theWiz;
    private Projectile projectile;
    private BglSprite[] lives = new BglSprite[MAX_LIFE];

    private Bat bat = new Bat();
    private Brectangle touchAreaThrowBall = new Brectangle(0,0, 1, 0.75f, 0, 0, 0, 0);
    private Brectangle touchAreaBat = new Brectangle(0,0, 1, 0.8f, 0, 0, 0, 0);
    private Bonus[] bonusT;
    private final ObjectPool bonusPool = new ObjectPool();
    private final Explosion[] exp = new Explosion[3];
    private final ObjectPool expPool = new ObjectPool();

   private int totalBricks;
    private int remainingBricks;
    private int remainingLife;
    private static enum GameState {ON, PAUSE, OFF};
    private GameState gameState = GameState.OFF;

    //constants
    private final float BONUS_PROBABILITY = 0.7f;

    //Layout constants
    private final float PADDX = 0.02f;
    private final float PADDY = 0.13f;
    private final float SIZEW = 0.14f;
    private final float SIZEH = 0.03f;
    private final int ROW = 5;
    private final int COL = 8;

    public SceneForBall() {
        super("myBalls");

        // WALLS CREATION
        Brectangle leftWall = new Brectangle(0-0.1f, 0, 0.1f, 1, 1, 1, 1, 1);
        Brectangle rightWall = new Brectangle(1, 0, 0.1f, 1, 1, 1, 1, 1 );
        Brectangle upperWall = new Brectangle(0, 0-0.1f, 1, 0.1f, 1, 1, 1, 1);
        add(upperWall);
        add(leftWall);
        add(rightWall);
        upperWall.setCollide(true);
        leftWall.setCollide(true);
        rightWall.setCollide(true);

        // BRICKS CREATION
        int index = 0;
        for (int i=0; i < ROW; i++){
            for (int j=0; j < COL; j++){
                destroyMe[index] = new Brick(1);
                destroyMe[index].setVisible(false);
                destroyMe[index].setCollide(true);
                add(destroyMe[index]);
                index++;
            }
        }
        totalBricks = index;
        remainingBricks = totalBricks;

        // BAT
        bat.setVisible(false);
        bat.setCollide(true);
        add(bat);

        // BALL CREATION
        myBall = new Ball(0.0f,0.0f, 0.05f, 0.03f, 0.1f, 0.7f, 1);
        myBall.setVisible(false);
        myBall.setCollide(true);
        myBall.collisionService.addCollider(leftWall);
        myBall.collisionService.addCollider(rightWall);
        myBall.collisionService.addCollider(leftWall);
        myBall.collisionService.addCollider(upperWall);
        myBall.collisionService.addCollider(destroyMe);
        myBall.collisionService.addCollider(bat);
        add(myBall);


        // Projectile
        projectile = new Projectile(0,0,0.06f,0.04f, new int[]{ R.drawable.projectile});
        projectile.setCollide(true);
        add(projectile);
        bat.collisionService.addCollider(projectile);
        projectile.setVisible(false);
        projectile.collisionService.addCollider(myBall);

        /*   WIZARD   */
        // Wizard Spritesheets
        SpriteSheet blank = new SpriteSheet(R.drawable.latiku, 2, 1, 2, new int[] {20, 20});
        SpriteSheet blank4 = new SpriteSheet(R.drawable.latiku_cast, 7, 1, 7,
                new int[] {4, 4, 4, 4, 4, 4, 8} );


        final SpriteSheet [] spriteSheetTab = new SpriteSheet[2];
        spriteSheetTab[0] = blank;
        spriteSheetTab[1] = blank4;
        // Actual wizard object
        theWiz = new Wizard(0.65f, 0.001f, 0.143f, 0.13f, spriteSheetTab, bat, projectile);
        // Wiz actions
        Action[] actions = new Action[3];
        actions[0] = new WizMove(theWiz);
        actions[1] = new WizCast(theWiz);
        actions[2] = new WizAttack(theWiz, projectile);
        theWiz.setActions(actions);
        theWiz.setCollide(false);
        theWiz.setVisible(false);
        add(theWiz);
        theWiz.setPos(0.65f, -0.035f);

        // Lives
        for (int i=0; i<MAX_LIFE; i++){
            float w = 0.1f;
            lives[i] = new BglSprite(0,0, 0.5f, 0.04f, new int[] {R.drawable.heart});
            lives[i].setSize(w);
            lives[i].setPos(1-i*w, 1, 1.5f, 1.5f);
            lives[i].setVisible(false);
            add(lives[i]);
        }

        // latiku test
        BglSprite l = new BglSprite(0,0,0.1f, 0.1f, new int[] {R.drawable.latikutest});
        l.setSize(0.125f);
        add(l);

        // BONUS/POOL CREATION
        bonusT = new Bonus[5];
        for (int i=0; i<bonusT.length; i++){
            bonusT[i] = new Bonus(0, 0, 0.03f, 0.02f, Bonus.BonusType.BALL_SPEED);
            bonusT[i].setVel(0,0);
            bonusT[i].setVisible(false);
            bonusT[i].setCollide(true);
            bonusT[i].collisionService.addCollider(bat);

            add(bonusT[i]);
        }
        bonusPool.setPool(bonusT);

        // MESSAGES
        MessageManager.addListener( "explosion", new Bcall<Void>() {
            @Override
            public Void call(Object o) {

                remainingBricks --;
                if (remainingBricks == 0){
                    stop();
                    SceneManager.startScene("startScene");
                }


                if (Math.random() > BONUS_PROBABILITY) {
                    Bonus bonus = (Bonus) bonusPool.getAvailableObj();
                    bonus.setPos(myBall.getPosX(), myBall.getPosY());
                    bonus.setVel(0, 0.25f);
                    bonus.setVisible(true);
                    bonus.setBonusType(Bonus.getRandomBonusType());
                }
                return null;
            }
        });

        MessageManager.addListener("explosion_end", new Bcall<Void>() {
            @Override
            public Void call(Object o) {
                expPool.release(o);
                return null;
            }
        });

        MessageManager.addListener( "lost_ball", new Bcall<Void>() {
            @Override
            public Void call(Object o) {
                playerFail();
                return null;
            }
        });

        MessageManager.addListener("bat_hit_by_projectile", new Bcall<Void>() {
            @Override
            public Void call(Object o) {
                //TODO change that it's not nice
                //So that there is only one collision registered
                bat.setCollide(false);
                playerFail();
                return null;
            }
        });


        /* BONUS MESSAGES */
        MessageManager.addListener( "bonus_out_of_screen", new Bcall <Void>() {
            @Override
            public Void call(Object o) {
                System.out.println(o);
                bonusPool.release(o);
                ((Bonus) o).setPos(0,0,0,0);
                ((Bonus) o).setVel(0,0);

                return null;
            }
        });

        MessageManager.addListener( "bonus_ball_speed", new Bcall <Void>() {
            @Override
            public Void call(Object o) {

                bonusPool.release(o);
                ((Bonus) o).setPos(0,0,0,0);
                ((Bonus) o).setVel(0,0);

                myBall.setSpeedFactor(2);

                new Btimer(BONUS_DURATION, new Callable(){
                    @Override
                    public Boolean call(){
                        if ( gameState == GameState.ON ) {
                            myBall.setSpeedFactor(1);
                        }
                        return false;
                    }
                });
                return null;
            }
        });

        MessageManager.addListener( "bonus_bat_size", new Bcall <Void>() {
        @Override
            public Void call(Object o) {

                bonusPool.release(o);
                ((Bonus) o).setPos(0,0,0,0);
                ((Bonus) o).setVel(0,0);

                bat.setBig(true);
                new Btimer(BONUS_DURATION, new Callable(){
                    @Override
                    public Boolean call(){
                        if ( gameState == GameState.ON ) {
                            bat.setBig(false);
                        }
                        return false;
                    }
                });
                return null;
            }
        });

        // TouchArea
        add(touchAreaThrowBall);
        touchAreaThrowBall.setCollide(false);

        touchAreaThrowBall.setTouchU(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (gameState == GameState.PAUSE) {
                    //TODO the following lines should be a function game_is_on ou quelque chose dans le genre
                    theWiz.setState(Wizard.WizState.ON);
                    bat.setCollide(true);
                    gameState = GameState.ON;
                    float newVx = InputStatus.touchXabsPerc - myBall.getPosX();
                    myBall.setSpeedFactor(1);
                    myBall.setVel(newVx, -0.45f);
                }
                return null;
            }
        });


        // Touch Area for Bat
        add(touchAreaBat);
        touchAreaBat.setCollide(false);
        touchAreaBat.setSize(1, 0.25f);
        touchAreaBat.setPos(0, 1-0.25f);

        touchAreaBat.setTouchD(new Callable<Void>() {
            public Void call() throws Exception {

                if(myBall.getVel().x != 0 && myBall.getVel().y != 0) {
                    bat.setToGoPos();
                }
                return null;
            }
        });

        touchAreaBat.setTouchM(new Callable<Void>() {
            public Void call() throws Exception {

                if(myBall.getVel().x != 0 && myBall.getVel().y != 0) {
                    bat.setToGoPos();
                }
                return null;
            }
        });

    }

    public void initBricks(){
        int index = 0;
        for (int i=0; i < ROW; i++){
            for (int j=0; j < COL; j++){
                destroyMe[index].setPos(PADDX + j*(SIZEW+0.01f), PADDY + i*(SIZEH+0.01f) );
                destroyMe[index].setSize(SIZEW, SIZEH);
                destroyMe[index].setCollide(true);
                destroyMe[index].setVisible(true);
                destroyMe[index].setRemainingHp(1);
                index++;
            }
        }
    }

    public void initGame(){
        remainingBricks = totalBricks;
        remainingLife = MAX_LIFE;
        myBall.setVisible(true);
        myBall.setSpeedFactor(0);
        myBall.setPos(0.5f, 0.8f, 0.5f, 0.5f);
        bat.setVisible(true);
        theWiz.setVisible(true);
        initBricks();
        lives[0].setVisible(true);
        lives[1].setVisible(true);
        lives[2].setVisible(true);
        gameState = GameState.PAUSE;
    }

    public void resetBallposition(){
        float posx = bat.getPosX();
        float posy = bat.getPosY();
        myBall.setPos(posx, posy-0.1f);
    }

    public void playerFail(){
        remainingLife --;
        lives[remainingLife].setVisible(false);
        gameState = GameState.PAUSE;
        theWiz.setState(Wizard.WizState.OFF);
        resetBallposition();
        myBall.setSpeedFactor(0);

        new Btimer(150, new Callable(){
                    @Override
                    public Boolean call(){
                        bat.init();
                        return false;
                    }
                });


        if ( remainingLife == 0 ) {
            stop();
            SceneManager.startScene("startScene");
        }
    }

    @Override
    public void start() {
        initGame();
        System.out.println("start balls!");
        SceneManager.setInputFocus(this);
    }

    @Override
    public void stop() {
        myBall.setSpeedFactor(0);
        myBall.setVisible(false);
        bat.setVisible(false);
        theWiz.setVisible(false);
        projectile.setVisible(false);
        gameState = GameState.OFF;
    }
}
