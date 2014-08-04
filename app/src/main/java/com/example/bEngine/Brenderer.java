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
import android.view.Choreographer;


public class Brenderer implements GLSurfaceView.Renderer {
    static final float movAveragePeriod = 35;
    static final float smoothFactor = 0.1f;
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
    private final float upX = 0.0f;
    private final float upY = 1.0f;
    private final float upZ = 0.0f;
    float smoothedDeltaRealTime_ms = 23;
    float movAverageDeltaTime_ms = smoothedDeltaRealTime_ms;
    private Context context;
    private SceneManager sManager;
    private ShaderList shaderList;
    private Shader shader;
    private BglObject objFollowed;
    private ArrayList<BglObject> objListCopy = new ArrayList();
    private float[] mvp = new float[16];
    private static float camX = 0.0f;
    private static float lookX = camX;
    private static float camY = 0.0f;
    private static float lookY = camY;
    private static float camZ = 0f;
    private static float lookZ = -1.0f;
    private static PointF camOffset;
    private static float camXO = 0;
    private static float camYO = 0;
    private static int screenW;
    private static int screenH;
    private Callable<Float> cb;


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
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleX(), 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleY(), 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleZ(), 0, 0, 1);
        Matrix.scaleM(modelMatrix, 0, farSizeWorld[0], farSizeWorld[1], farSizeWorld[2]);

        /*View matrix*/
        if (obj.glService.isBoundToCamera()) {

            if (camOffset == null) {
                // World coordinate wise the camera is initially in (0.5 0.5)
                camOffset = new PointF(0.5f - pos.x, 0.5f - pos.y);
                camXO = camXO - pos.x;
                camYO = camYO - pos.y;
            }

            moveCam(pos.x + camOffset.x, pos.y + camOffset.y);
        }

        if (obj.getDisregardCam()) {
            Matrix.setIdentityM(viewMatrix, 0);
        } else {
            Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
        }

        /* MVP magic */
        Matrix.setIdentityM(mvp, 0);
        Matrix.multiplyMM(mvp, 0, viewMatrix, 0, modelMatrix, 0);
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

    public void lockCamera(BglObject obj) {
        objFollowed = obj;
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

        SceneManager.update(17);
        InputStatus.updateObjectsTouchStates(screenW, screenH);

        glClear(GL_COLOR_BUFFER_BIT);
		/* I - Scene enumeration */

        //TODO le sceneManager etant un singleton pourquoi est-ce un member de cette classe????
        //TODO a priori ca ne devrait pas l'etre et je devrais juste me servir de getInstance.
        for (Scene scene : sManager.getScenes()) {
            if (scene.getVisible()) {
                /* II - Objects enumeration */
                // copy the content of the list to avoid racing condition and ... crash
                objListCopy.addAll(scene.getMembers());
                for (BglObject obj : objListCopy) {
                    //TODO the list shouldnt contain null element.
                    //Maybe it does because whenever I add an object to it, I add it at the very end of
                    //the list et je me soucie pas du fais que y'a des trous dedans a boucher de temps en temps
                    if (obj != null && obj.isVisible()) {
                        calculateMVP(obj);

                        //TODO maybe this allocates a bit of memory
                        shader = shaderList.getProg(obj.glService.getShaderName());
                        //TODO have a draw function
                        glUseProgram(shader.get_program());
                        shader.sendParametersToShader(obj, mvp);
                        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
                    }
                }
                objListCopy.clear();
            }
        }


    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projMatrix, 45, (float) width / (float) height, 1f, 200f);
        Matrix.invertM(projMatrixInv, 0, projMatrix, 0);
        screenW = width;
        screenH = height;
    }

}
