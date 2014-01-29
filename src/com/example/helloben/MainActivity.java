package com.example.helloben;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import com.example.bEngine.Brenderer;
import com.example.bEngine.BtextureManager;
import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.World;
import com.example.bEngine.Brenderer;
import com.example.bEngine.object.BglAnimatedSprite;
import com.example.bEngine.object.BglSprite;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.object.SpriteSheet;
import com.example.bEngine.shader.ShaderList;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private GLSurfaceView theGLView;
    private World theWorld;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        final Point screenSize = new Point();
        display.getSize(screenSize);

        /* Leave this comment with the line below
           This can only be done in the onSurfaceCreated of the renderer! */
        final ShaderList shaderlist = new ShaderList( this );

        final BtextureManager textureManager = BtextureManager.getInstance();
        textureManager.fillTextureHashTable(R.drawable.lastlayer);
        textureManager.fillTextureHashTable(R.drawable.dune);
        textureManager.fillTextureHashTable(R.drawable.firstlayer);
        textureManager.fillTextureHashTable(R.drawable.sprite);
        textureManager.fillTextureHashTable(R.drawable.sprite2);


        theWorld = new World(screenSize);
        theGLView = new GLSurfaceView(this);

        final BglSprite clouds = new BglSprite( 0, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        /////////////////////////
        theWorld.addHabitant(clouds);
        clouds.anchorPointSet(0.0f,0.0f);
        clouds.zSet(0.7f);

        final BglSprite clouds2 = new BglSprite( 1.3f, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        /////////////////////////
        theWorld.addHabitant(clouds2);
        clouds2.anchorPointSet(0.0f,0.0f);
        clouds2.zSet(0.7f);

        final BglSprite dune = new BglSprite( 0, 0.75f, 2.5f,0.44f, R.drawable.dune );
        /////////////////////////
        theWorld.addHabitant(dune);
        dune.anchorPointSet(0,1);
        dune.zSet(0.4f);

        final BglSprite bg1 = new BglSprite( 0, 1, 2.5f, 1f, R.drawable.firstlayer );
        ////////////////////////
        theWorld.addHabitant(bg1);
        bg1.anchorPointSet(0,1);


        SpriteSheet slug0 = new SpriteSheet(R.drawable.sprite, 12, 1, 12);
        SpriteSheet slug = new SpriteSheet(R.drawable.sprite2, 16, 1, 16);
        final SpriteSheet [] spriteSheetTab = new SpriteSheet[2];
        spriteSheetTab[0] = slug0;
        spriteSheetTab[1] = slug;

        final Heroe metal = new Heroe(0.0f, 0.8f, 0.135f, 0.2f, spriteSheetTab);
        theWorld.addHabitant(metal);
        metal.anchorPointSet(0.0f,1.0f);
        metal.setAngleY(180);

        Callable onSurfaceCreatedCb = new Callable() {
            @Override
            public Float call() {

                clouds.setTextureHandle( R.drawable.lastlayer );
                clouds2.setTextureHandle( R.drawable.lastlayer );
                bg1.setTextureHandle( R.drawable.firstlayer );
                dune.setTextureHandle( R.drawable.dune );

                metal.setTextureHandle( spriteSheetTab );

                return 0f;
            }
        };


        theGLView.setEGLContextClientVersion(2);
        final Brenderer theRenderer = new Brenderer( this, theWorld, textureManager, onSurfaceCreatedCb );
        theGLView.setRenderer(theRenderer);
        theGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


//        metal.setBoundToCamera(true);

//        final  Brectangle rect = new Brectangle(0.5f,0,0.2f,0.2f,0.5f,0.7f, 0.9f, 0.5f);
//        theWorld.addHabitant(rect);


        final Joypad theJoypad = new Joypad();
        theWorld.addHabitant(theJoypad);

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {

            theRenderer.moveCam( theWorld.getCamPos().x + ( InputStatus.touch.x - theJoypad.prev.x) / (float) screenSize.x,
                    theWorld.getCamPos().y + (InputStatus.touch.y - theJoypad.prev.y) / (float) screenSize.y );

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
                bg1.setTextureHandle(textureManager.findHandle(R.drawable.firstlayer));


            return 0f;
            }
        };


        theJoypad.defineActionMove( joypadActionMove );
        theJoypad.defineActionDown( joypadActionDown );

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
        Timer myTimer = new Timer ();
        TimerTask renderTask = new TimerTask() {
            public void run() {
                theGLView.requestRender();
            }
        };
        myTimer.scheduleAtFixedRate( renderTask ,100, 30);

        setContentView(theGLView);

	}

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();
        System.out.println( e.getActionMasked() );
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

        theWorld.updateTouchStates();

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // pauses the rendering thread.
        // Desalocate stuff if we get hungry
        theGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resumes a paused rendering thread.
        theGLView.onResume();
    }

}