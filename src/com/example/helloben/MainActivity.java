package com.example.helloben;

import java.util.Timer;
import java.util.TimerTask;

import com.example.BGL.InputStatus;
import com.example.BGL.World;
import com.example.BGL.MyRenderer;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private GLSurfaceView mGLView;
    private World mWorld;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mWorld = new World();
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

        BglAnimatedSprite metal = new BglAnimatedSprite(200,200,250,250, spriteSheetTab);
        mWorld.addHabitant(metal);
        metal.anchorPointSet(0.5f,0.5f);
*/

   //     Brectangle myRect = new RectangleRotate(100,100,500,500,0.6f, 0.7f,1.0f);
   //     mWorld.addHabitant(myRect);
   //     myRect.posSet(100,100);
        RectangleRotateGrid myGrid = new RectangleRotateGrid(10, 10, 10, 14);
        for ( int i =0;i<10*14;i++)
            mWorld.addHabitant( myGrid.getRects(i) );
     //   AnimatedObject metal2 = new AnimatedObject(480,200,250,250,R.drawable.sprite2, 16, 1, 16);
     //   mWorld.addHabitant(metal2);



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
