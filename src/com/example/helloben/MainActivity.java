package com.example.helloben;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.World;
import com.example.bEngine.MyRenderer;
import com.example.bEngine.object.BglSprite;

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
        Point screenSize = new Point();
        display.getSize(screenSize);

        theWorld = new World(screenSize);
        theGLView = new GLSurfaceView(this);

        theGLView.setEGLContextClientVersion(2);
        final MyRenderer theRenderer = new MyRenderer( this, theWorld );
        theGLView.setRenderer(theRenderer);
        theGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);





        BglSprite clouds = new BglSprite( 0, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        theWorld.addHabitant(clouds);
        clouds.anchorPointSet(0.0f,0.0f);
        clouds.zSet(0.7f);
        BglSprite clouds2 = new BglSprite( 1.3f, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        theWorld.addHabitant(clouds2);
        clouds2.anchorPointSet(0.0f,0.0f);
        clouds2.zSet(0.7f);
        BglSprite dune = new BglSprite( 0, 0.75f, 2.5f,0.44f, R.drawable.dune );
        theWorld.addHabitant(dune);
        dune.anchorPointSet(0,1);
        dune.zSet(0.4f);
        BglSprite bg1 = new BglSprite( 0, 1, 2.5f, 1f, R.drawable.firstlayer );
        theWorld.addHabitant(bg1);
        bg1.anchorPointSet(0,1);

        Joypad theJoypad = new Joypad();
        theWorld.addHabitant(theJoypad);

        theJoypad.defineActionDown( new Callable() {
            @Override
            public Float call() {
                theRenderer.move();
                return 0.1f;
            }
        } );

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

class MyGLSurfaceView extends GLSurfaceView {

    private final Renderer theRenderer;
    private World mWorld;

    public MyGLSurfaceView(Context context, World mWorld) {
        super(context);

        this.mWorld = mWorld;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        theRenderer = new MyRenderer( context, mWorld );
        setRenderer(theRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

/*      //A sprite made of 2 differents sprite sheet
        SpriteSheet slug0 = new SpriteSheet(R.drawable.sprite, 12, 1, 12);
        SpriteSheet slug = new SpriteSheet(R.drawable.sprite2, 16, 1, 16);
        SpriteSheet [] spriteSheetTab = new SpriteSheet[2];
        spriteSheetTab[0] = slug0;
        spriteSheetTab[1] = slug;

        BglAnimatedSprite metal = new BglAnimatedSprite(1.0f, 0.5f, 0.5f, 0.5f, spriteSheetTab);
        mWorld.addHabitant(metal);
        metal.anchorPointSet(0.5f,0.5f);
*/

        BglSprite clouds = new BglSprite( 0, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        mWorld.addHabitant(clouds);
        clouds.anchorPointSet(0.0f,0.0f);
        clouds.zSet(0.7f);
        BglSprite clouds2 = new BglSprite( 1.3f, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        mWorld.addHabitant(clouds2);
        clouds2.anchorPointSet(0.0f,0.0f);
        clouds2.zSet(0.7f);

        BglSprite dune = new BglSprite( 0, 0.75f, 2.5f,0.44f, R.drawable.dune );
        mWorld.addHabitant(dune);
        dune.anchorPointSet(0,1);
        dune.zSet(0.4f);

        BglSprite bg1 = new BglSprite( 0, 1, 2.5f, 1f, R.drawable.firstlayer );
        mWorld.addHabitant(bg1);
        bg1.anchorPointSet(0,1);


        Joypad theJoypad = new Joypad();
        mWorld.addHabitant(theJoypad);

        theJoypad.defineActionDown( new Callable() {
            @Override
            public Float call() {
                // do stuff!!!
                return 0.1f;
            }
        } );
/*
        queueEvent( new Runnable() {
            public void run(){
                theRenderer.move(0.1f,0.1f);
            }

        });

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


        Timer myTimer = new Timer ();
        TimerTask renderTask = new TimerTask() {
        	public void run() {
                requestRender();
        	}
        };
        myTimer.scheduleAtFixedRate( renderTask ,100, 30);

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

        mWorld.updateTouchStates();

        return true;
    }
}

