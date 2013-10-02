package com.example.BGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.opengl.GLES20;

public class BglObject {
	
	protected Point pos;
	private float z;
	protected Point anchor;
	private int [] angle = new int [3];
	protected Point speed;
	protected int w;
	protected int h;
	protected Bitmap bitmap;
	public Rect rectangle;
    protected boolean pressed = false;
    int texture_id;

	protected final int [] textureHandle = new int [2];

	
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
    	1.0f, 0.0f, 0.0f,
    };
	
    // tableaux qui contiennent les donnes de nos vertex?
    private final FloatBuffer vertexBuffer;
    // TODO should be final, cf AnimatedObject, je devrais pas overwriter en gros. Une implementation
    // TODO differente pour les objects animes de ceux pas animes
    protected FloatBuffer[] textCoordBuffer;
    
    protected  Shader mShader;
    
	public BglObject( int x, int y, int w, int h, int texture_id ){

		pos = new Point(x,y);
		anchor = new Point (w/2, h/2);
		speed = new Point(2,2);
		z = 0;

        this.texture_id = texture_id;

		angle[0] = 0;
		angle[1] = 0;
		angle[2] = 0;
		this.w = w;
		this.h = h;
		
		rectangle = new Rect( pos.x-w/2, pos.y-h/2, pos.x + w/2, pos.y + h/2 );

		// obj coord for gl TODO TODO TODO
		ByteBuffer bb = ByteBuffer.allocateDirect( objCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(objCoords);
        vertexBuffer.position(0);
        // texture coord
    	ByteBuffer bb2 = ByteBuffer.allocateDirect(textcoords.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        textCoordBuffer = new FloatBuffer[1];
        textCoordBuffer[0] = bb2.asFloatBuffer();
        textCoordBuffer[0].put(textcoords);
        textCoordBuffer[0].position(0);

        //default shader an object is drawn with
        //mShader = ShaderList.effects[0];
	}

    public void loadTexture(Context context){
        //create a bitmap, from image to pixel data, has to be done whenever we reload the
        //texture since we "recycle" the bitmap at the end
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), texture_id, options);

        mShader.loadTexture(bitmap, textureHandle);
        bitmap.recycle();
    }

    public void initShader(Shader shader){
        mShader = shader;
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

        rectangle.set(pos.x, pos.y, pos.x + this.w, pos.y + this.h);
	}

    // Finger touches the object
	public void touchDown() {
	}
	// Finger realeases when over the object
	public void touchUp() {
	}
    //When moving your finger over the object
	public void touchMove() {
	}
    // Triggered when releasing the finger and it's not over the object anymore
    public void touchUpMove(int x, int y){
    }

    public boolean isPressed(){
        return pressed;
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
        rectangle.set(pos.x-w/2, pos.y-h/2, pos.x + w/2, pos.y + h/2);
	}

    public void update(){
        rectangle.set(pos.x-w/2, pos.y-h/2, pos.x + w/2, pos.y + h/2);
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
	
	public FloatBuffer textCoordBufferGet() {
		return textCoordBuffer[0];
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
