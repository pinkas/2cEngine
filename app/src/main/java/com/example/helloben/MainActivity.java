package com.example.helloben;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.example.bEngine.Brenderer;
import com.example.bEngine.BtextureManager;
import com.example.bEngine.InputStatus;
import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.helloben.HelloBenScenes.GameScene;
import com.example.helloben.HelloBenScenes.SplashScreen;
import com.example.helloben.HelloBenScenes.TestScene;


import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends Activity {

    private GLSurfaceView theGLView;
    private SceneManager sceneManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        final Point screenSize = new Point();
        display.getSize(screenSize);

        final BtextureManager textureManager = BtextureManager.getInstance();
        /* TODO pass a table of resource rather than 10000 calls to fillTextureHashTable */
        textureManager.fillTextureHashTable(R.drawable.lastlayer);
        textureManager.fillTextureHashTable(R.drawable.dune);
        textureManager.fillTextureHashTable(R.drawable.firstlayer);
        textureManager.fillTextureHashTable(R.drawable.sprite);
        textureManager.fillTextureHashTable(R.drawable.sprite2);
        textureManager.fillTextureHashTable(R.drawable.splash);
        textureManager.fillTextureHashTable(R.drawable.biker);
        textureManager.fillTextureHashTable(R.drawable.blank);
        textureManager.fillTextureHashTable(R.drawable.road);

     //   Scene testScene = new TestScene();
   //     sceneManager.setFocusScene(oneScene);

        // TODO if splashscreen is created before GameScene then it is under my gameScene despite the
        // TODO setFocus. need to have a setLayer for Scenes as well
        //final Scene splashScreen = new SplashScreen();
        final Scene gameScene = new GameScene();

        SceneManager.setFocusScene(gameScene);


        //TODO Why this Callback passed to the REnderer???
        final Brenderer theRenderer = new Brenderer( this, SceneManager.getInstance(), textureManager, new Callable() {
            @Override
            public Float call() {
                return 0f;
            }
        } );

        theGLView = new GLSurfaceView(this);
        setContentView(theGLView);
        theGLView.setEGLContextClientVersion(2);
        theGLView.setRenderer(theRenderer);
        theGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        TextView text = new TextView(this);
        text.setText("blah!!!!!!");


//        metal.setBoundToCamera(true);

        /*
        final Joypad theJoypad = new Joypad();
        splashScreen.add( theJoypad );

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {
        //    splashScreen.stop();
            float x = sceneManager.getCamPos().x + ( InputStatus.touch.x - theJoypad.prev.x) / (float) screenSize.x;
            float y = sceneManager.getCamPos().y + (InputStatus.touch.y - theJoypad.prev.y) / (float) screenSize.y;
            theRenderer.moveCam( x, y );
            theJoypad.prev.x = InputStatus.touch.x;
            theJoypad.prev.y = InputStatus.touch.y;
            return 0f;
            }
        };

        Callable joypadActionDown = new Callable() {
            @Override
            public Float call() {
            theJoypad.prev.x = InputStatus.touch.x;
            theJoypad.prev.y = InputStatus.touch.y;
            return 0f;
            }
        };

        theJoypad.defineActionMove( joypadActionMove );
        theJoypad.defineActionDown( joypadActionDown );
*/



/*      //To test the dirty hack for multi layers bg
        float Z2 = 0.9f;
        float pos = 0.5f;
        float dev = 0.01f;
        Brectangle rect3 = new Brectangle( pos, pos, 0.1f, 0.3f, 1.0f, 1.0f, 1.0f );
        mWorld.addHabitant(rect3);
        rect3.anchorPointSet(0.0f, 0.0f);
        Brectangle rect4 = new Brectangle( pos, pos, 0.1f, 0.3f, 0.5f, 0.2f, 0.2f );
        mWorld.addHabitant(rect4);
        rect4.anchorPointSet(0.0f, 0.0f);
        rect4.zSet(Z2);
*/
/*      //Grid of rectangles that rotate
        myRect.posSet(100,100);
        RectangleRotateGrid myGrid = new RectangleRotateGrid(1, 1, 10, 14);
        for ( int i =0;i<10*14;i++)
            mWorld.addHabitant( myGrid.getRects(i) );
        AnimatedObject metal2 = new AnimatedObject(480,200,250,250,R.drawable.sprite2, 16, 1, 16);
        mWorld.addHabitant(metal2);
*/

        //GameLoop myLoop = new GameLoop(theGLView, screenSize);
        //ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        //exec.scheduleAtFixedRate( myLoop, 0, 7, TimeUnit.MILLISECONDS);
/*
        Timer myTimer = new Timer ();
        TimerTask renderTask = new TimerTask() {
            long mLastTime = 0;
            public void run() {

                long now = System.currentTimeMillis();
                System.out.println( now - mLastTime );
                mLastTime = now;

                InputStatus.updateTouchStates( screenSize.x, screenSize.y );
                SceneManager.update();
                theGLView.requestRender();
            }
        };
        myTimer.scheduleAtFixedRate( renderTask ,1000, 20);
*/
	}

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                InputStatus.setTouchDown((int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                InputStatus.setTouchMove((int) x, (int) y);
                break;
            case MotionEvent.ACTION_UP:
                InputStatus.setTouchUp((int) x, (int) y);
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*pauses the rendering thread, Desalocate stuff if we get hungry*/
        theGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Resumes a paused rendering thread */
        theGLView.onResume();
    }

}