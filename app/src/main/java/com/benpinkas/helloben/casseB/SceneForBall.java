package com.benpinkas.helloben.casseB;

import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.ObjectPool;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.service.Bcall;
import com.benpinkas.bEngine.service.MessageManager;

import java.util.concurrent.Callable;


public class SceneForBall extends Scene {

    private Ball myBall;
    private Brick[] destroyMe = new Brick[300];
    private Bat bat = new Bat();
    private Brectangle touchAreaThrowBall = new Brectangle(0,0, 1, 0.8f, 0, 0, 0, 0);
    private Bonus[] bonusT;
    private final ObjectPool bonusPool = new ObjectPool();

    private static int MAX_LIFE = 3;
    private int totalBricks;
    private int remainingBricks;
    private int remainingLife;
    private static enum GameState {ON, PAUSE, OFF};
    private GameState gameState = GameState.OFF;

    //Layout constants
    private final float PADDX = 0.02f;
    private final float PADDY = 0.1f;
    private final float SIZEW = 0.11f;
    private final float SIZEH = 0.04f;
    private final int ROW = 7;
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
                destroyMe[index] = new Brick();
                destroyMe[index].setVisible(false);
                destroyMe[index].setCollide(true);
                add(destroyMe[index]);

                //add(destroyMe[index].getParticles(0));
                //add(destroyMe[index].getParticles(1));
                //add(destroyMe[index].getParticles(2));
                //add(destroyMe[index].getParticles(3));
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
        myBall = new Ball(0.5f,0.8f, 0.045f, 0.025f, 0.1f, 0.7f, 1);
        myBall.setVisible(false);
        myBall.setCollide(true);
        myBall.collisionService.addCollider(leftWall);
        myBall.collisionService.addCollider(rightWall);
        myBall.collisionService.addCollider(leftWall);
        myBall.collisionService.addCollider(upperWall);
        myBall.collisionService.addCollider(destroyMe);
        myBall.collisionService.addCollider(bat);
        add(myBall);

        // BONUS/POOL CREATION
        bonusT = new Bonus[5];
        for (int i=0; i<bonusT.length; i++){
            bonusT[i] = new Bonus(0, 0, 0.02f, 0.015f, Bonus.BonusType.BALL_SPEED);
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

                if (Math.random() > 0.3) {
                    Bonus bonus = (Bonus) bonusPool.getAvailableObj();
                    bonus.setPos(myBall.getPosX(), myBall.getPosY());
                    bonus.setVel(0,0.25f);
                    bonus.setVisible(true);
                }
                return null;
            }
        });

        MessageManager.addListener( "lost_ball", new Bcall<Void>() {
            @Override
            public Void call(Object o) {
                remainingLife --;
                gameState = GameState.PAUSE;
                resetBallposition();
                myBall.setSpeedFactor(0);
                if ( remainingLife == 0 ) {
                    stop();
                    SceneManager.startScene("startScene");
                }
                return null;
            }
        });


        /* BONUS MESSAGES */

        MessageManager.addListener( "bonus_out_of_screen", new Bcall <Void>() {
            @Override
            public Void call(Object o) {
                System.out.println(o);
                bonusPool.release((Bonus)o);
                ((Bonus) o).setPos(0,0,0,0);
                ((Bonus) o).setVel(0,0);

                return null;
            }
        });

        MessageManager.addListener( "bonus_ball_speed", new Bcall <Void>() {
            @Override
            public Void call(Object o) {

                bonusPool.release((Bonus)o);
                ((Bonus) o).setPos(0,0,0,0);
                ((Bonus) o).setVel(0,0);

                myBall.setSpeedFactor(2);

                new Btimer(300, new Callable(){
                    @Override
                    public Boolean call(){
                        myBall.setSpeedFactor(1);
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
                    gameState = GameState.ON;
                    float newVx = InputStatus.touchXabsPerc - myBall.getPosX();
                    myBall.setSpeedFactor(1);
                    myBall.setVel(newVx, -0.45f);
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
               // destroyMe[index].setColor(0.5f, 1, 0.5f, 1.0f);
                //destroyMe[index].setColor(j * 0.05f, j * 0.1f, 1.0f, 1.0f);
                destroyMe[index].initParticles();
                destroyMe[index].setCollide(true);
                destroyMe[index].setVisible(true);
                index++;
            }
        }
    }

    public void initGame(){
        remainingBricks = totalBricks;
        remainingLife = MAX_LIFE;
        initBricks();
        myBall.setVisible(true);
        myBall.setSpeedFactor(0);
        bat.setVisible(true);
        gameState = GameState.PAUSE;
    }

    public void resetBallposition(){
        float posx = bat.getPosX();
        float posy = bat.getPosY();
        myBall.setPos(posx, posy-0.1f);
    }

    @Override
    public void start() {
        initGame();
        SceneManager.setInputFocus(this);
    }

    @Override
    public void stop() {
        myBall.setSpeedFactor(0);
        myBall.setVisible(false);
        bat.setVisible(false);
        gameState = GameState.OFF;
    }
}
