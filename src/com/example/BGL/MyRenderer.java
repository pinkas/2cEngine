package com.example.BGL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.BGL.object.BglObject;
import com.example.BGL.shader.ShaderList;
import com.example.BGL.utils.MatrixHelper;

import android.content.Context;
import android.graphics.Point;
import static android.opengl.GLES20.*;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyRenderer implements GLSurfaceView.Renderer {
	private Context context;
    private static final String TAG = "MyGLRenderer";
    private World mWorld;
    
    private int screen_width;
    private int screen_height;
    

    private final float[] mProjMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] invM = new float[16];
    private float[] mvp = new float[16];

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
    	
    	Point pos = obj.posGet();
    	Point anchor = obj.anchorPointGet();
    	//convert to GL coordinate
    	float x = ( (float) (pos.x + anchor.x) / screen_width )*2 - 1;
        float y = -( ( (float) (pos.y + anchor.y) / screen_height )*2 - 1);
        float z = obj.zGet();
        
        Matrix.invertM(invM, 0, mProjMatrix, 0);
        final float[] farPointNdc = { x, y, z, 1};
        final float[] farPointWorld = new float[4];        
        Matrix.multiplyMV(farPointWorld, 0, invM, 0, farPointNdc, 0);
          
        Matrix.setIdentityM(modelMatrix, 0);
        
        farPointWorld[0] /= farPointWorld[3];
        farPointWorld[1] /= farPointWorld[3];
        farPointWorld[2] /= farPointWorld[3];
        
    	float w = (float) obj.widthGet() / screen_width ;
        float h = (float) obj.heightGet() / screen_height;  
        
        final float[] farSize = { w, h, 0, 1 };
        final float[] farSizeWorld = new float[4];
        Matrix.multiplyMV(farSizeWorld, 0, invM, 0, farSize, 0 );
        
        farSizeWorld[0] /= farSizeWorld[3];
        farSizeWorld[1] /= farSizeWorld[3];
        farSizeWorld[2] /= farSizeWorld[3];       
        
        Matrix.translateM(modelMatrix, 0, farPointWorld[0], farPointWorld[1], farPointWorld[2]);     
        Matrix.rotateM(modelMatrix, 0, obj.getAngleX(), 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleY(), 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, obj.getAngleZ(), 0, 0, 1);
        Matrix.scaleM(modelMatrix, 0,  farSizeWorld[0], farSizeWorld[1], farSizeWorld[2] );
        
        Matrix.setIdentityM(mvp, 0);
        Matrix.multiplyMM(mvp, 0, mProjMatrix, 0, modelMatrix, 0);
        
        return mvp;
    	
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Draw background color
        glClear(GL_COLOR_BUFFER_BIT);

        mWorld.update();

        List<BglObject> habitants = mWorld.getHabitants();
		Enumeration<BglObject> e = Collections.enumeration( habitants );
		// Draw the habitants \o/
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
        
        MatrixHelper.perspectiveM(mProjMatrix, 45, (float) width / (float) height, 1f, 200f);
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = glGetError()) != GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

}
