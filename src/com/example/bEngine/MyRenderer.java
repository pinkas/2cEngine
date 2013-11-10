package com.example.bEngine;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.shader.ShaderList;

import android.content.Context;

import static android.opengl.GLES20.*;

import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyRenderer implements GLSurfaceView.Renderer {
	private Context context;
    private static final String TAG = "MyGLRenderer";
    private World mWorld;
    
    private int screen_width;
    private int screen_height;

    private BglObject objFollowed;

    private final float[] projMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] projMatrixInv = new float[16];
    private float[] mvp = new float[16];


    private float camX = 0.0f;
    private float camY = 0.0f;
    private float camZ = 0.0f;
    private float lookX = camX;
    private float lookY = camY;
    private float lookZ = -1.0f;

    private final float upX = 0.0f;
    private final float upY = 1.0f;
    private final float upZ = 0.0f;
    private float camXO=0;
    private float camYO=0;
    private float camXSC =0;
    private float camYSC=0;


    public MyRenderer ( Context context, World mWorld) {
		super();
		this.context = context;
		this.mWorld = mWorld;
	}
	
    
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        glClearColor(0.0f, 0.2f, 0.6f, 0.2f);

        /* IMPORTANT COMMENT
         Shaders have to be compiled here, Texture have to be loaded here!!!!!!

         load all the texture whenever the context is created (first time or
         when resuming the app). OnSurfaceCreated is the only place i know where i can load
        Textures/Resources */

        // Load all the shaders
        // TODO have a list that we go through
        ShaderList shaderlist = new ShaderList(context);

        // load all the texture and init the existing object of the world with a shader

        /* TODO
         The shader list a member of the world ?
         The world decides what shader to render what object depending on what's happening
         "Oh this object should be rendered by this shader, Oh no that one actually..."
        */
        mWorld.loadObjectTexture(context, shaderlist);

    }
    
    public float[] calculateMVP( BglObject obj ) {

        /* so that i can "cheat with multi layered bg scrolling*/
        boolean perspective_scorll=false;

    	PointF pos = obj.posGet();
        PointF size= obj.sizeGet();
    	PointF anchor = obj.anchorPointGet();
        //convert obj pos from absolute (but relative to screen size ) to absolute pixel coord
        float x = (pos.x + (0.5f - anchor.x) * size.x);
        float y = (pos.y + (0.5f - anchor.y) * size.y);
    	//convert to GL coordinate
    	x = x * 2 - 1;
        y = 1 -  y * 2;
        float z = obj.zGet();

        if (z != 0){
            perspective_scorll = true;
        }

        final float[] farPointWorld;
        farPointWorld = fromWorldToGlFar(x,y,z);

        final float[] farSizeWorld;
         farSizeWorld = fromWorldToGlFar(size.x,size.y,0);

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

            //TODO why would the object contains that???? the renderer should contain it!!!!
            if ( obj.getCameraOffset() == null ){
                PointF offset = new PointF( camX - farPointWorld[0], camY - farPointWorld[1] );
                obj.setOffsetCamera( offset );
                camXO = camXO -pos.x;
                camYO = camYO - pos.y;
            }

            // Camera position in world coordinate
            camXSC = pos.x + camXO;
            camYSC = pos.y + camYO;
            mWorld.setCamPos(camXSC, camYSC );
            // Camera position in GL coordinate
            camX = farPointWorld[0] + obj.getCameraOffset().x;
            camY = farPointWorld[1] + obj.getCameraOffset().y;
            camZ = 0;
            lookX = camX;
            lookY = camY;
        }
        camZ = 0;
        Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, lookX, lookY, lookZ, upX, upY, upZ);
        /* MVP magic */
        Matrix.setIdentityM(mvp, 0);
        Matrix.multiplyMM(mvp, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvp, 0, projMatrix, 0, mvp, 0);
        
        return mvp;
    }

    public void moveCam( float x, float y){

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

        // Draw background color
        glClear(GL_COLOR_BUFFER_BIT);

        mWorld.update();

        List<BglObject> habitants = mWorld.getHabitants();
		Enumeration<BglObject> e = Collections.enumeration( habitants );
		/* Draw the habitants */
		while( e.hasMoreElements() ) {
			BglObject obj = e.nextElement();

			mvp = calculateMVP( obj );
			obj.draw(mvp);
		}    
        
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
    	
    	screen_width = width;
    	screen_height = height;
        glViewport(0, 0, width, height);
        
        MatrixHelper.perspectiveM( projMatrix, 45, (float) width / (float) height, 1f, 200f );
        Matrix.invertM(projMatrixInv, 0, projMatrix, 0);
    }

    public void lockCamera(BglObject obj){
        objFollowed = obj;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = glGetError()) != GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
