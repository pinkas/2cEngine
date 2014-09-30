package com.benpinkas.helloben;

import java.util.concurrent.Callable;

import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.BtextureManager;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.helloben.casseB.SceneForBall;
import com.benpinkas.R;
import com.benpinkas.helloben.casseB.StartScene;


import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private GLSurfaceView theGLView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        /* TODO pass a table of resource rather than 10000 calls to fillTextureHashTable */
        BtextureManager.fillTextureHashTable(R.drawable.lastlayer);
        BtextureManager.fillTextureHashTable(R.drawable.dune);
        BtextureManager.fillTextureHashTable(R.drawable.firstlayer);
        BtextureManager.fillTextureHashTable(R.drawable.sprite);
        BtextureManager.fillTextureHashTable(R.drawable.sprite2);
        BtextureManager.fillTextureHashTable(R.drawable.splash);
        BtextureManager.fillTextureHashTable(R.drawable.biker);
        BtextureManager.fillTextureHashTable(R.drawable.blank);
        BtextureManager.fillTextureHashTable(R.drawable.road);
        BtextureManager.fillTextureHashTable(R.drawable.start);
        BtextureManager.fillTextureHashTable(R.drawable.dust);

        // TODO if splashscreen is created before GameScene it is under my gameScene despite the
        // TODO setFocus. need to have a setLayer for Scenes as well
/*
        final Scene splashScreen = new SplashScreen();
        splashScreen.setVisible(false);
*/
//        final Scene gameScene = new GameScene();
//        gameScene.setVisible(true);

//        final Scene testScene = new TestScene();
//        testScene.setVisible(false);

  //      final Scene multiLayerTest = new MultiLayersBackground();
  //      multiLayerTest.setVisible(false);

//        final Scene rotateRectangles = new RotateGridRectangles();
//        rotateRectangles.setVisible(false);

        final Scene myballs = new SceneForBall();
        final Scene startScene = new StartScene();

        SceneManager.setInputFocus(startScene);
        SceneManager.startScene(startScene);


        //TODO Why this Callback passed to the Renderer???
        final Brenderer theRenderer = new Brenderer( this, new Callable() {
            @Override
            public Float call() {
                return 0f;
            }
        } );

        theGLView = new GLSurfaceView(this);
        setContentView(theGLView);
        theGLView.setEGLContextClientVersion(2);
        theGLView.setRenderer(theRenderer);
       // theGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();
        switch (e.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                InputStatus.setTouchPrev((int) x, (int) y);
                InputStatus.setTouchDown((int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                InputStatus.setTouchPrev(InputStatus.touchXscr, InputStatus.touchYscr);
                InputStatus.setTouchMove((int) x, (int) y);
                break;
            case MotionEvent.ACTION_UP:
                InputStatus.setTouchUp((int) x, (int) y);
                break;
            default:
                return false;
        }
        InputStatus.setTouchDelta();
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