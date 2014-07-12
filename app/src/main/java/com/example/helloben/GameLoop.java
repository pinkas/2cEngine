package com.example.helloben;

import android.app.Activity;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import com.example.bEngine.InputStatus;
import com.example.bEngine.scene.SceneManager;

/**
 * Created by Ben on 12-May-14.
 */
public class GameLoop implements Runnable {

    private GLSurfaceView renderer;
    private Point screensize;

    private float current;
    private float past;

    private static float dt;

    public GameLoop ( GLSurfaceView surfaceView, Point screensize){
        renderer = surfaceView;
        this.screensize = screensize;
    }

    @Override
    public void run(){

        current = SystemClock.uptimeMillis();
        dt = current - past;


        InputStatus.updateObjectsTouchStates(screensize.x, screensize.y);


        long currTimePick_ms= SystemClock.uptimeMillis();
        System.out.println( "DEMANDE  "  + currTimePick_ms);
        renderer.requestRender();

        past = current;

    }

    public static float getDt() {
        return dt;
    }
}
