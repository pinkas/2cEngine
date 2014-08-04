package com.example.bEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.bEngine.shader.Shader;
import com.example.bEngine.shader.ShaderList;
import com.example.helloben.GameLoop;

import android.content.Context;

import static android.opengl.GLES20.*;

import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;


public class Brenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private static float camXworld = 0.5f;
    private static float camYworld = 0.5f;
    private static final float[] projMatrix = new float[16];
    private static final float[] modelMatrix = new float[16];
    private static final float[] viewMatrix = new float[16];
    private static final float[] projMatrixInv = new float[16];

    private static final float[] farPointNdc = new float[4];
    private static final float[] farPointWorld = new float[4];
    private static final float[] farSizeWorld = new float[4];
    private static final float[] camTODOFIXME = new float[4];
    private static final float upX = 0.0f;
    private static final float upY = 1.0f;
    private static final float upZ = 0.0f;
    private Context context;
    private Shader shader;
    private ArrayList<BglObject> objListCopy = new ArrayList();
    private static float[] mvp = new float[16];
    private static float camX = 0.0f;
    private static float lookX = camX;
    private static float camY = 0.0f;
    private static float lookY = camY;
    private static float camZ = 0.0f;
    private static float lookZ = -1.0f;

    private static int screenW;
    private static int screenH;
    private Callable<Float> cb;

    private static final float NS_PER_SEC = 1000000000;
    private static float prev;
    private static final float MAX_FRAME_DELTA_SEC = 0.1f;


    public Brenderer(Context context, Callable<Float> cb) {
        super();
        this.context = context;

        this.cb = cb;
    }

    public static float getCamPosX() {
        return camXworld;
    }

    public static float getCamPosY() {
        return camYworld;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = glGetError()) != GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        glClearColor(0.0f, 0.3f, 0.6f, 0.2f);
        // TODO have a list that we go through
        new ShaderList(context);
        // load all the texture and init the existing object of the world with a shader
        BtextureManager.loadAll(context);
        /* TODO consider finding anonther way to do the following?? */
        for (Scene scene : SceneManager.getScenes()) {
            BglObject[]  blah= scene.getMembers();
            for (int i=0; i < blah.length; i++ ) {
                if (blah[i] == null)
                    continue;
                blah[i].glService.setTextureHandle(blah[i].getRes());
            }
        }
    }

    public void calculateMVP(BglObject obj) {

        /*
        if (obj.glService.isBoundToCamera()) {

            if (camOffset == null) {
                // World coordinate wise the camera is initially in (0.5 0.5)
                camOffset = new PointF(0.5f - pos.x, 0.5f - pos.y);
                camXO = camXO - pos.x;
                camYO = camYO - pos.y;
            }

            moveCam(pos.x + camOffset.x, pos.y + camOffset.y);
        }
*/
        if (obj.getDisregardCam())
            Matrix.setIdentityM(viewMatrix, 0);

        float[] blah = obj.getMvp();
        /* MVP magic */
        Matrix.setIdentityM(mvp, 0);
        Matrix.multiplyMM(mvp, 0, viewMatrix, 0, blah, 0);
        Matrix.multiplyMM(mvp, 0, projMatrix, 0, mvp, 0);
    }

    public static void calculateMVP(BglObject obj, float[] mvp) {

        Matrix.setIdentityM(mvp, 0);

        boolean perspective_scorll = false;

        float objX = obj.getPosX();
        float objY = obj.getPosY();
        float sizeW = obj.getSizeW();
        float sizeH = obj.getSizeH();

        /* From "relative size" screen coordinate to GL */
        float xGl = (objX + sizeW / 2f) * 2f - 1;
        float yGl = 1 - (objY + sizeH / 2f) * 2f;
        float z = obj.getZ();

        if (z != 0) perspective_scorll = true;

        Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
        MatrixHelper.perspectiveM(projMatrix, 45, (float) 720 / (float) 1280, 1f, 200f);
        Matrix.invertM(projMatrixInv, 0, projMatrix, 0);

        fromWorldToGlFar(xGl, yGl, z, farPointWorld);
        fromWorldToGlFar(sizeW, sizeH, 0, farSizeWorld);

        /* ugly hack for multi layered scrolling background */
        float div = 1 - z;
        if (perspective_scorll) {
            farSizeWorld[0] = farSizeWorld[0] / div;
            farSizeWorld[1] = farSizeWorld[1] / div;
        }

        /* Calculate model matrix */
        Matrix.translateM(mvp, 0, farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        Matrix.rotateM(mvp, 0, obj.getAngleX(), 1, 0, 0);
        Matrix.rotateM(mvp, 0, obj.getAngleY(), 0, 1, 0);
        Matrix.rotateM(mvp, 0, obj.getAngleZ(), 0, 0, 1);
        Matrix.scaleM(mvp, 0, farSizeWorld[0], farSizeWorld[1], farSizeWorld[2]);
/*
        if (obj.glService.isBoundToCamera()) {

            if (camOffset == null) {
                // World coordinate wise the camera is initially in (0.5 0.5)
                camOffset = new PointF(0.5f - pos.x, 0.5f - pos.y);
                camXO = camXO - pos.x;
                camYO = camYO - pos.y;
            }
            moveCam(pos.x + camOffset.x, pos.y + camOffset.y);
        }
*/
        if (obj.getDisregardCam()) Matrix.setIdentityM(viewMatrix, 0);

        /* MVP magic */
        Matrix.multiplyMM(mvp, 0, viewMatrix, 0, mvp, 0);
        Matrix.multiplyMM(mvp, 0, projMatrix, 0, mvp, 0);
    }

    public static void moveCam(float x, float y) {
        camXworld = x;
        camYworld = y;
        x = x * 2 - 1;
        y = 1 - y * 2;
        fromWorldToGlFar(x, y, 0f, camTODOFIXME);
        camX = camTODOFIXME[0];
        camY = camTODOFIXME[1];
        lookX = camX;
        lookY = camY;
        Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

    public static int getScreenW() {
        return screenW;
    }

    public static int getScreenH() {
        return screenH;
    }

    public static float getCamXworld() {
        return camXworld;
    }

    public static float getCamYworld() {
        return camYworld;
    }

    public static float getCamY() {
        return camY;
    }

    public static float getCamX() {
        return camX;
    }

    public static void fromWorldToGlFar(float x, float y, float z, float[] pointInput) {

        farPointNdc[0] = x;
        farPointNdc[1] = y;
        farPointNdc[2] = z;
        farPointNdc[3] = 1;

        pointInput[0] = pointInput[1] = pointInput[2] = pointInput[3] = 0;

        Matrix.multiplyMV(pointInput, 0, projMatrixInv, 0, farPointNdc, 0);
        pointInput[0] /= pointInput[3];
        pointInput[1] /= pointInput[3];
        pointInput[2] /= pointInput[3];
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        if ( prev == 0) {
            prev = System.nanoTime();
            return;
        }

        float now = System.nanoTime();
        float dt = (now - prev) / NS_PER_SEC;
        System.out.println(now-prev);
        prev = now;

        if (dt > MAX_FRAME_DELTA_SEC) {
            dt = MAX_FRAME_DELTA_SEC;
        }

        //InputStatus.updateObjectsTouchStates(screenW, screenH);
        //TODO update les "updateOnlyMembers" cf update method in SceneManager
        SceneManager.updateTimers(dt);
        Scene focusScene = SceneManager.getInputFocus();

        glClear(GL_COLOR_BUFFER_BIT);

		// 1 - Scene enumeration
        for (Scene scene : SceneManager.getScenes())
        {
            if (scene.getVisible())
            {
               shader = ShaderList.getProg( "rect" );
               glUseProgram( 9 );
               shader.prepare();

                // 2 - Objects enumeration
                BglObject[] members = scene.getMembers();
                for (BglObject obj : members)
                {
                    if (obj == null)
                        continue;

                    if (scene == focusScene) {
                        InputStatus.updateObjecTouchState(obj, screenW, screenH);
                    }

                    obj.update(dt);

                    if (obj.visible)
                    {
                        if (obj.dirty)
                        {
                            if(obj.collide)
                            {
                                for(BglObject obj2 : members)
                                {
                                    if(obj2==null)
                                        continue;

                                    if (!obj2.visible || !obj2.collide || obj == obj2)
                                        continue;
                                    if ( obj.collisionService.collide(obj, obj2)) {
                                        obj.collisionService.fixPos(obj, obj2);
                                    }
                                }
                            }

                            calculateMVP(obj, obj.getMvp());
                            obj.dirty = false;
                        }

                        //shader = ShaderList.getProg(obj.glService.getShaderName());
                        //glUseProgram( shader.get_program() );
                        //shader.prepare();
//                        System.out.println(obj);
                        shader.sendParametersToShader(obj, obj.getMvp());
                        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
                    }
                }
//                InputStatus.resetTouch();
            }
        }
    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        screenW = width;
        screenH = height;
    }

}
