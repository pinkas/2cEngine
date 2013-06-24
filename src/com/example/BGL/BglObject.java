package com.example.BGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.opengl.GLES20;

public class BglObject {
	
	private Point pos;
	private float z;
	private Point anchor;
	private int [] angle = new int [3];
	private Point speed;
	private int w;
	private int h;
	private final Bitmap bitmap;

	private final int [] textureHandle = new int [2];
	
	
    // define the shape, a square.
    private float objCoords[] = { -1, 1, 0,   // top left
                                  -1, -1, 0,   // bottom left
                                   1, -1, 0,   // bottom right
                                   1,  1, 0 }; // top right
    // we show the whole texture, nothing less, nothing more
    static float textcoords[] = {
    	0.0f, 0.0f, 0.0f,
    	0.0f, 1.0f, 0.0f,
    	1.0f, 1.0f, 0.0f,
    	1,0f, 1.0f, 0.0f
    };
	
    // tableaux qui contiennent les donnes de nos vertex?
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer textBuffer;
    
    private  Shader mShader;
    
	public BglObject( int x, int y, int w, int h, Context context, int texture_id ){

		this.pos = new Point(x,y);
		this.anchor = new Point (w/2, h/2);
		this.speed = new Point(2,2);
		this.z =0;
		
		this.angle[0] = 0;
		this.angle[1] = 0;
		this.angle[2] = 0;
		this.w = w;
		this.h = h;
		
		// obj coord for gl TODO TODO TODO
		ByteBuffer bb = ByteBuffer.allocateDirect( objCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(objCoords);
        vertexBuffer.position(0);
        // texture coord
    	ByteBuffer bb2 = ByteBuffer.allocateDirect(textcoords.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        textBuffer = bb2.asFloatBuffer();
        textBuffer.put(textcoords);
        textBuffer.position(0);      
        
        //create a bitmap, from image to pixel data
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), texture_id, options);
        
        //default shader an object is drawn with
        //mShader = ShaderList.effects[0];
        mShader = new BasicShader(context);
        mShader.loadTexture(bitmap, textureHandle);
        bitmap.recycle();  
	}
	
	
	public void anchorPointSet( float x, float y ) {
		this.anchor.x =  (int) (w/2 - (x*w));
		this.anchor.y =  (int) (h/2 - (y*h));
	}
	
	public Point anchorPointGet() {
		return anchor; 
	}

	public void resize(int w, int h){
		//TODO  check if parameters are correct?
		this.w = w;
		this.h = h;
	}
	
	public int widthGet() {
		return  w;
	}
	
	public int heightGet() {
		return  h;
	}
	
	public float [] getVertexCoords() {
		return  objCoords;
	}
	
	public Point posGet () {
		return pos;
	}
	
	public void zSet() {
		this.z += 0.005;
	}
	
	public float zGet() {
		return z;
	}
	
	public void posSet ( Point pos ) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
	
	public Point speedGet() {
		return speed;
	}
	
	public void speedSet( Point speed ) {
		this.speed.x = speed.x;
		this.speed.y = speed.y;
	}
	
	public void angleSet ( int index, int angle ) {
		this.angle[index] = angle; 
	}
	
	public int angleGet(int index) {
		return this.angle[index];
	}
	
	public FloatBuffer vertexBufferGet() {
		return vertexBuffer;
	}
	
	public FloatBuffer textBufferGet() {
		return textBuffer;
	}
	
	public int  textureHandleGet() {
		return textureHandle[0];
	}
	
	public void draw( float[] mat ) {
		GLES20.glUseProgram( mShader.get_program() );
		mShader.setuniform(this, mat);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
	}
	
}
