package com.example.helloben;

import java.util.Timer;
import java.util.TimerTask;

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

    public MyGLSurfaceView(Context context, World mWorld) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        mRenderer = new MyRenderer( context, mWorld );
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
        Timer myTimer = new Timer ();
        TimerTask renderTask= new TimerTask() {
        	public void run() {
        		
        		requestRender();
        	}
        };
        
        myTimer.scheduleAtFixedRate( renderTask ,100, 10 );
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	World.setTouch( (int) x, (int) y );
            	System.out.println( " TOUCHED");
        }

        return true;
    }
}
