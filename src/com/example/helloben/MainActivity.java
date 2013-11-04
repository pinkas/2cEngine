package com.example.helloben;

import java.util.Timer;
import java.util.TimerTask;

import com.example.BGL.InputStatus;
import com.example.BGL.World;
import com.example.BGL.MyRenderer;
import com.example.BGL.object.BglAnimatedSprite;
import com.example.BGL.object.BglSprite;
import com.example.BGL.object.Brectangle;
import com.example.BGL.object.SpriteSheet;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private GLSurfaceView mGLView;
    private World mWorld;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);

        mWorld = new World(screenSize);
        mGLView = new MyGLSurfaceView(this, mWorld);
        setContentView(mGLView);
	}

    @Override
    protected void onPause() {
        super.onPause();
        // pauses the rendering thread.
        // Desalocate stuff if we get hungry
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resumes a paused rendering thread.
        mGLView.onResume();
    }

}

class MyGLSurfaceView extends GLSurfaceView {

    private final Renderer mRenderer;
    private World mWorld;

    public MyGLSurfaceView(Context context, World mWorld) {
        super(context);

        this.mWorld = mWorld;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        mRenderer = new MyRenderer( context, mWorld );
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
/*
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
/*
        BglSprite clouds3 = new BglSprite( 0f, 0f, 1.0f, 0.3f, R.drawable.lastlayer );
        mWorld.addHabitant(clouds3);
        clouds3.anchorPointSet(0.0f,0.0f);
        clouds3.zSet(0.5f);

*/
        float Z1 = 0.4f;
        BglSprite dune = new BglSprite( 0, 0.75f, 2.5f,0.44f, R.drawable.dune );
        mWorld.addHabitant(dune);
        dune.anchorPointSet(0,1);
        dune.zSet(Z1);

        BglSprite bg1 = new BglSprite( 0, 1, 2.5f, 1f, R.drawable.firstlayer );
        mWorld.addHabitant(bg1);
        bg1.anchorPointSet(0,1);
/*
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

/*

        Brectangle rectClouds = new Brectangle( 0.5f,0.5f,0.3f, 0.5f, 1.0f, 1.0f, 1.0f );
        mWorld.addHabitant(rectClouds);
        rectClouds.anchorPointSet(0.5f, 0.5f);

        Brectangle rect2 = new Brectangle( 0.5f, 0.5f, 0.6f, 1.0f, 0.5f, 0.2f, 0.2f );
        mWorld.addHabitant(rect2);
        rect2.anchorPointSet(0.5f, 0.5f);
        rect2.zSet(0.5f);
*/
/*
        CubeScroll myRect = new CubeScroll(0.0f, 1.0f, 0.12f, 0.2f,0.0f, 0.7f,1.0f);
        mWorld.addHabitant(myRect);
        myRect.anchorPointSet(0.0f,1.0f);
   //    myRect.setBoundToCamera(true);
*/

/*
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
