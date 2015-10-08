package com.benpinkas.helloben;


import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.BtextureManager;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.service.Bcall;
import com.benpinkas.bEngine.service.MessageManager;
import com.benpinkas.helloben.casseB.SceneForBall;
import com.benpinkas.R;
import com.benpinkas.helloben.casseB.SplashScene;
import com.benpinkas.helloben.casseB.StartScene;


import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private GLSurfaceView theGLView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        /* TODO pass a table of resource rather than 10000 calls to fillTextureHashTable */
        BtextureManager.fillTextureHashTable(R.drawable.bg);
        BtextureManager.fillTextureHashTable(R.drawable.brick_mario);
        BtextureManager.fillTextureHashTable(R.drawable.ball);
        BtextureManager.fillTextureHashTable(R.drawable.bat);
        BtextureManager.fillTextureHashTable(R.drawable.latiku);
        BtextureManager.fillTextureHashTable(R.drawable.latiku_cast);
        BtextureManager.fillTextureHashTable(R.drawable.projectile);
        BtextureManager.fillTextureHashTable(R.drawable.dust);
        BtextureManager.fillTextureHashTable(R.drawable.start);



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



        final Brenderer theRenderer = new Brenderer( this );

        theGLView = new GLSurfaceView(this);
        setContentView(theGLView);

        // Will not run in emulator otherwise ...
        if (Build.HARDWARE.contains("goldfish")) {
            theGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        }

        theGLView.setEGLContextClientVersion(2);
        theGLView.setRenderer(theRenderer);
       // theGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        MessageManager.addListener("oscgl", new Bcall<Void>() {
            @Override
            public Void call(Object o) {
                final Scene startScene = new StartScene();
                final Scene myballs = new SceneForBall();
                final Scene splahsScene = new SplashScene();

                SceneManager.setInputFocus(startScene);
                SceneManager.startScene(splahsScene);
                return null;
            }
        });
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