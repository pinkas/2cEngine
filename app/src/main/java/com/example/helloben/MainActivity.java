package com.example.helloben;

import java.util.concurrent.Callable;

import com.example.bEngine.Brenderer;
import com.example.bEngine.BtextureManager;
import com.example.bEngine.InputStatus;
import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.helloben.HelloBenScenes.GameScene;
import com.example.helloben.HelloBenScenes.MultiLayersBackground;
import com.example.helloben.HelloBenScenes.RotateGridRectangles;
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

        // TODO if splashscreen is created before GameScene it is under my gameScene despite the
        // TODO setFocus. need to have a setLayer for Scenes as well
        final Scene splashScreen = new SplashScreen();
        splashScreen.setVisible(false);

        final Scene gameScene = new GameScene();
        gameScene.setVisible(false);

        final Scene testScene = new TestScene();
        testScene.setVisible(true);

        final Scene multiLayerTest = new MultiLayersBackground();
        multiLayerTest.setVisible(false);

        final Scene rotateRectangles = new RotateGridRectangles();
        rotateRectangles.setVisible(false);

        SceneManager.setInputFocus(testScene);


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
        theGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
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
                InputStatus.setTouchPrev(InputStatus.touch.x, InputStatus.touch.y);
                InputStatus.setTouchMove((int) x, (int) y);
                break;
            case MotionEvent.ACTION_UP:
                InputStatus.setTouchUp((int) x, (int) y);
                break;
            default:
                return false;
        }
        InputStatus.setTouchDelta(InputStatus.touchPrev, InputStatus.touch );
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