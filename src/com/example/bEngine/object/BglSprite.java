package com.example.bEngine.object;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.bEngine.BtextureManager;
import com.example.bEngine.shader.Shader;

import static android.opengl.GLES20.glGenTextures;

public class BglSprite extends BglObject {

	protected Bitmap bitmap;
    protected int texture_id;
	protected int [] textureHandle = new int [1];
    protected FloatBuffer[] textCoordBuffer;
    // we show the whole texture, nothing less, nothing more
    protected static float textcoords[] = {
    	0.0f, 0.0f, 0.0f,
    	0.0f, 1.0f, 0.0f,
    	1.0f, 1.0f, 0.0f,
    	1.0f, 0.0f, 0.0f,
    };

	public BglSprite( float x, float y, float w, float h, int texture_id ){

        super(x,y,w,h);

        shaderName = "basic";
        this.texture_id = texture_id;
        // texture coord
    	ByteBuffer bb = ByteBuffer.allocateDirect(textcoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textCoordBuffer = new FloatBuffer[1];
        textCoordBuffer[0] = bb.asFloatBuffer();
        textCoordBuffer[0].put(textcoords);
        textCoordBuffer[0].position(0);

	}


    public void setTextureHandle( int androidResource ){
        final BtextureManager textManager = BtextureManager.getInstance();
        textureHandle[0] = textManager.findHandle(androidResource);
    }

    @Override
	public int  textureHandleGet() {
		return textureHandle[0];
	}

    @Override
    public float[] getColor(){
        float color[] = new float[4];
        return color;
    }

    public FloatBuffer textCoordBufferGet() {
        return textCoordBuffer[0];
    }


}
