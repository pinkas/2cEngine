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

import android.content.Context;

import static android.opengl.GLES20.*;

import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class Brenderer implements GLSurfaceView.Renderer {
	private Context context;
    private static final String TAG = "MyGLRenderer";
    private SceneManager sManager;
    private BtextureManager textureManager;

    private ShaderList shaderList;
    
    private BglObject objFollowed;

    private ArrayList <BglObject> objListCopy = new ArrayList();

    private final float[] projMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] projMatrixInv = new float[16];
    private float[] mvp = new float[16];


    private float camX = 0.0f;
    private float camY = 0.0f;
    private float camZ = 0f;
    private float lookX = camX;
    private float lookY = camY;
    private float lookZ = -1.0f;

    private float camXworld = 0.5f;
    private float camYworld = 0.5f;

    private PointF camOffset;

    private final float upX = 0.0f;
    private final float upY = 1.0f;
    private final float upZ = 0.0f;
    private float camXO=0;
    private float camYO=0;

    private Callable<Float> cb;


    public Brenderer ( Context context, SceneManager sManager, BtextureManager textureManager, Callable<Float> cb ) {
		super();
		this.context = context;
		this.sManager = sManager;
        this.textureManager = textureManager;

        this.cb = cb;
	}

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        glClearColor(0.0f, 0.3f, 0.6f, 0.2f);
        // TODO have a list that we go through
        shaderList = new ShaderList(context);
        // load all the texture and init the existing object of the world with a shader
        textureManager.loadAll( context );
        /* TODO consider finding anonther way to do the following?? */
        for (Scene scene : sManager.getScenes()) {
            for ( BglObject obj : scene.getMembers() ) {
                obj.setTextureHandle();
            }
        }
    }
    
    public float[] calculateMVP( BglObject obj ) {

        /* so that i can "cheat with multi layered bg scrolling*/
        boolean perspective_scorll=false;

    	PointF pos = obj.getPos();
        PointF size = obj.getSize();
    	PointF anchor = obj.anchorPointGet();
        /* Take into account anchor point */
        float x = (pos.x + (0.5f - anchor.x) * size.x);
        float y = (pos.y + (0.5f - anchor.y) * size.y);
    	/* From "relative size" screen coordinate to GL */
    	float xGl = x * 2 - 1;
        float yGl = 1 -  y * 2;
        float z = obj.zGet();

        if (z != 0){
            perspective_scorll = true;
        }

        final float[] farPointWorld = fromWorldToGlFar(xGl,yGl,z);
        final float[] farSizeWorld = fromWorldToGlFar(size.x,size.y,0);

        /* ugly hack for multi layered scrolling background */
        float div = 1 - z;
        if (perspective_scorll) {
            farSizeWorld[0] = farSizeWorld[0]/div;
            farSizeWorld[1] = farSizeWorld[1]/div;
        }

        /* Calculate model matrix */
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleX(), 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleY(), 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleZ(), 0, 0, 1);
        Matrix.scaleM(modelMatrix, 0,  farSizeWorld[0], farSizeWorld[1], farSizeWorld[2] );

        /*View matrix*/
        if ( obj.getBoundToCamera() ) {

            if ( camOffset == null ){
                // World coordinate wise the camera is initially in (0.5 0.5)
                camOffset = new PointF( 0.5f - pos.x, 0.5f - pos.y );
                camXO = camXO - pos.x;
                camYO = camYO - pos.y;
            }

            moveCam( pos.x + camOffset.x, pos.y + camOffset.y);
        }

        if (obj.getDisregardCam()){
            Matrix.setIdentityM(viewMatrix, 0);
        }
        else {
            Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
        }

        /* MVP magic */
        Matrix.setIdentityM(mvp, 0);
        Matrix.multiplyMM(mvp, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvp, 0, projMatrix, 0, mvp, 0);

        return mvp;
    }

    public void moveCam( float x, float y){
        camXworld = x;
        camYworld = y;
        x = x * 2 - 1;
        y = 1 -  y * 2;
        final float[] pos = fromWorldToGlFar(x, y, 0);
        camX = pos[0];
        camY = pos[1];
        lookX = camX;
        lookY = camY;
        /* Camera position in world coordinate */
        sManager.setCamPos( camXworld, camYworld );
    }

    public void lockCamera(BglObject obj){
        objFollowed = obj;
    }

    public float[] fromWorldToGlFar( float x, float y, float z){

        final float[] farPointNdc = { x, y, z, 1 };
        final float[] farPointWorld = new float[4];
        Matrix.multiplyMV(farPointWorld, 0, projMatrixInv, 0, farPointNdc, 0);
        farPointWorld[0] /= farPointWorld[3];
        farPointWorld[1] /= farPointWorld[3];
        farPointWorld[2] /= farPointWorld[3];
        return farPointWorld;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        /* clear with a lovely color */
        glClear(GL_COLOR_BUFFER_BIT);
		/* I - Scene enumeration */

        //TODO le sceneManager etant un singleton pourquoi est-ce un member de cette classe????
        //a priori ca ne devrait pas l'etre et je devrais juste me servir de getInstance.
        for ( Scene scene : sManager.getScenes() ){
            if ( scene.getVisible() ){
                /* II - Objects enumeration */
                // copy the content of the list to avoid racing condition and ... crash
                objListCopy.addAll( scene.getMembers() );
                for ( BglObject obj : objListCopy ) {
                    //TODO the list shouldnt contain null element.
                    //Maybe it does because whenever I add an object to it, I add it at the very end of
                    //the list et je me soucie pas du fais que y'a des trous dedans a boucher de temps en temps
                    if ( obj != null && obj.isVisible() ){
                        final Shader shader = shaderList.getProg( obj.getShaderName() );
                        mvp = calculateMVP( obj );
                        obj.draw(mvp, shader);
                    }
                }
                objListCopy.clear();
            }
        }
    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM( projMatrix, 45, (float) width / (float) height, 1f, 200f );
        Matrix.invertM(projMatrixInv, 0, projMatrix, 0);
    }


    public static void checkGlError(String glOperation) {
        int error;
        while ((error = glGetError()) != GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
