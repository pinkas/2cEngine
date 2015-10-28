package com.benpinkas.helloben.leCasseBrique.scene;

import com.benpinkas.R;
import com.benpinkas.bEngine.Action;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.ObjectPool;
import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.service.Bcall;
import com.benpinkas.bEngine.service.MessageManager;
import com.benpinkas.helloben.leCasseBrique.Ball;
import com.benpinkas.helloben.leCasseBrique.Bat;
import com.benpinkas.helloben.leCasseBrique.Bonus;
import com.benpinkas.helloben.leCasseBrique.Mission;
import com.benpinkas.helloben.leCasseBrique.missions.MissionList;
import com.benpinkas.helloben.leCasseBrique.wizard.Projectile;
import com.benpinkas.helloben.leCasseBrique.wizard.WizAttack;
import com.benpinkas.helloben.leCasseBrique.wizard.WizCast;
import com.benpinkas.helloben.leCasseBrique.wizard.WizMove;
import com.benpinkas.helloben.leCasseBrique.wizard.Wizard;

import java.util.concurrent.Callable;

public class GameScene extends Scene {

    private final float BONUS_PROBABILITY = 0.3f;
    private final Projectile projectile;
    private final Wizard theWizard;

    private enum GameState {ON, OFF}
    private GameState gameState = GameState.OFF;

    private final static int MAX_LIFE = 3;
    private int lifeCount = MAX_LIFE;
    private BglSprite[] lives = new BglSprite[MAX_LIFE];

    private Ball theBall = new Ball(0.0f,0.0f, 0.05f, 0.03f, 0.1f, 0.7f, 1);
    private Bat theBat = new Bat();

    private Brectangle touchAreaThrowBall = new Brectangle(0,0, 1, 0.75f, 0, 0, 0, 0);
    private Brectangle touchAreaBat = new Brectangle(0,0, 1, 0.8f, 0, 0, 0, 0);

    private final ObjectPool bonusPool = new ObjectPool();
    private final ObjectPool expPool = new ObjectPool();

    private Mission currentMission;
    private int missionIndex = 0;
    private int brickCount;

    public GameScene() {
        super("GameScene");

        // Invisible walls
        Brectangle leftWall = new Brectangle(0-0.1f, 0, 0.1f, 1, 1, 1, 1, 1);
        Brectangle rightWall = new Brectangle(1, 0, 0.1f, 1, 1, 1, 1, 1 );
        Brectangle upperWall = new Brectangle(0, 0-0.1f, 1, 0.1f, 1, 1, 1, 1);
        add(upperWall);
        add(leftWall);
        add(rightWall);
        upperWall.setCollide(true);
        leftWall.setCollide(true);
        rightWall.setCollide(true);

        // Lives
        for (int i=0; i<MAX_LIFE; i++) {
            float w = 0.1f;
            lives[i] = new BglSprite(0,0, 0.5f, 0.04f, new int[] {R.drawable.heart});
            lives[i].setSize(w);
            lives[i].setPos(1-i*w, 1, 1.5f, 1.5f);
            lives[i].setVisible(false);
            add(lives[i]);
        }

        // Bat
        theBat.setVisible(false);
        theBat.setCollide(true);
        add(theBat);

        // Ball
        theBall = new Ball(0.0f,0.0f, 0.05f, 0.03f, 0.1f, 0.7f, 1);
        theBall.setVisible(false);
        theBall.setCollide(true);
        theBall.collisionService.addCollider(leftWall);
        theBall.collisionService.addCollider(rightWall);
        theBall.collisionService.addCollider(leftWall);
        theBall.collisionService.addCollider(upperWall);
        theBall.collisionService.addCollider(theBat);
        add(theBall);

        // Projectile
        projectile = new Projectile(0,0,0.06f,0.04f, new int[]{ R.drawable.projectile});
        projectile.setCollide(true);
        add(projectile);
        theBat.collisionService.addCollider(projectile);
        projectile.setVisible(false);
        projectile.collisionService.addCollider(theBall);

        // The baddy
        SpriteSheet blank = new SpriteSheet(R.drawable.latiku, 2, 1, 2, new int[] {20, 20});
        SpriteSheet blank4 = new SpriteSheet(R.drawable.latiku_cast, 7, 1, 7,
                new int[] {4, 4, 4, 4, 4, 4, 8} );
        final SpriteSheet [] spriteSheetTab = new SpriteSheet[2];
        spriteSheetTab[0] = blank;
        spriteSheetTab[1] = blank4;
        // Actual wizard object
        theWizard = new Wizard(0.65f, 0.001f, 0.143f, 0.13f, spriteSheetTab, theBat, projectile);
        theWizard.setSize(0.17f);
        // Wiz actions
        Action[] actions = new Action[3];
        actions[0] = new WizMove(theWizard);
        actions[1] = new WizCast(theWizard);
        actions[2] = new WizAttack(theWizard, projectile);
        theWizard.setActions(actions);
        theWizard.setCollide(false);
        theWizard.setVisible(false);
        add(theWizard);
        theWizard.setReferenceHeight(-0.027f);
        theWizard.setPos(0.65f, -0.035f);

        // BONUS/POOL CREATION
        Bonus[] bonusT = new Bonus[5];
        for (int i=0; i<bonusT.length; i++){
            bonusT[i] = new Bonus(0, 0, 0.03f, 0.02f, Bonus.BonusType.BALL_SPEED);
            bonusT[i].setVel(0,0);
            bonusT[i].setVisible(false);
            bonusT[i].setCollide(true);
            bonusT[i].collisionService.addCollider(theBat);
            add(bonusT[i]);
        }

        bonusPool.setPool(bonusT);

        // Mission
        currentMission = MissionList.getMissions()[0];

        // Area for initial throw
        add(touchAreaThrowBall);
        touchAreaThrowBall.setCollide(false);

        touchAreaThrowBall.setTouchU(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (gameState == GameState.OFF) {
                    startGame();
                }
                return null;
            }
        });

        // Touch Area for Bat
        add(touchAreaBat);
        touchAreaBat.setCollide(false);
        touchAreaBat.setSize(1, 0.25f);
        touchAreaBat.setPos(0, 1 - 0.25f);

        touchAreaBat.setTouchD(new Callable<Void>() {
            public Void call() throws Exception {
                if (gameState == GameState.ON) {
                    theBat.setToGoPos();
                }
                return null;
            }
        });

        touchAreaBat.setTouchM(new Callable<Void>() {
            public Void call() throws Exception {
                if (gameState == GameState.ON) {
                    theBat.setToGoPos();
                }
                return null;
            }
        });

        // Messages
        MessageManager.addListener("lost_ball", new Bcall<Void>() {
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
                theBat.setCollide(false);
                playerFail();
                return null;
            }
        });

        MessageManager.addListener("explosion", new Bcall<Void>() {
            @Override
            public Void call(Object o) {
                brickCount --;
                if (brickCount == 0){
                    nextMission();
                    return null;
                }
                if (Math.random() > BONUS_PROBABILITY) {
                    initBonus();
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

    }

    public void initBonus() {
        Bonus bonus = (Bonus) bonusPool.getAvailableObj();
        bonus.setPos(theBall.getPosX(), theBall.getPosY());
        bonus.setVel(0, 0.25f);
        bonus.setVisible(true);
        bonus.setBonusType(Bonus.getRandomBonusType());
    }

    public BglObject getTheBall(){
        return theBall;
    }

    public void resetBallposition(){
        float posx = theBat.getPosX();
        float posy = theBat.getPosY();
        theBall.setPos(posx, posy-0.1f);
    }

    public void playerFail(){
        lifeCount --;
        lives[lifeCount].setVisible(false);
        gameState = GameState.OFF;
        theWizard.setState(Wizard.WizState.OFF);
        resetBallposition();
        theBall.setSpeedFactor(0);

        new Btimer(150, new Callable(){
            @Override
            public Boolean call(){
                theBat.init();
                return false;
            }
        });

        if (lifeCount == 0) {
            stop();
            SceneManager.startScene("startScene");
        }
    }

    public void startGame() {
        gameState = GameState.ON;
        theBat.setCollide(true);
        float newVx = InputStatus.touchXabsPerc - theBall.getPosX();
        theBall.setSpeedFactor(1);
        theBall.setVel(newVx, -0.45f);
        theWizard.setState(Wizard.WizState.ON);
    }

    public void initGame() {
        lifeCount = MAX_LIFE;
        missionIndex = 0;
        currentMission = MissionList.getMissions()[missionIndex];
        brickCount = currentMission.addBricksToScene(this);
        currentMission.init();
        theBall.setVisible(true);
        theBall.setSpeedFactor(0);
        theBall.setPos(0.5f, 0.8f, 0.5f, 0.5f);
        theBat.setVisible(true);
        theWizard.setVisible(true);
        gameState = GameState.OFF;
        for (BglSprite l : lives) {
            l.setVisible(true);
        }
    }

    public void nextMission() {
        missionIndex++;
        if (missionIndex >= MissionList.getMissions().length) {
            // You beat the game, how impressive
            SceneManager.startScene("splashScene");
            return;
        } else {
            currentMission = MissionList.getMissions()[missionIndex];
            brickCount = currentMission.addBricksToScene(this);
            currentMission.init();
            // the following comes from playerFail and should be in a function
            gameState = GameState.OFF;
            theWizard.setState(Wizard.WizState.OFF);
            resetBallposition();
            theBall.setSpeedFactor(0);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (currentMission.completed()) {
            // do transition to next mission, load it, start it
        }
    }

    @Override
    public void start() {
        initGame();
    }

    @Override
    public void stop() {

    }
}
