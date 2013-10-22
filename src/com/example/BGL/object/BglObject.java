package com.example.BGL.object;

import android.content.Context;
import android.opengl.GLES20;

import com.example.BGL.shader.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ben on 10/10/13.
 */
public class BglObject extends Bobject {

    protected Shader mShader;

    private float objCoords[] = {
            -1, 1, 0,    // top left
            -1, -1, 0,   // bottom left
             1, -1, 0,   // bottom right
             1,  1, 0 }; // top right

    private final FloatBuffer vertexBuffer;

    public BglObject( float x, float y, float w, float h){
        super(x,y,w,h);

        ByteBuffer bb = ByteBuffer.allocateDirect( objCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(objCoords);
        vertexBuffer.position(0);
    }

    public void initShader(Shader shader){
        mShader = shader;
    }

    public FloatBuffer vertexBufferGet() {
        return vertexBuffer;
    }

    public void draw( float[] mat ) {
        GLES20.glUseProgram(mShader.get_program());
        mShader.sendParametersToShader(this, mat);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }


    public boolean hasTexture(){
        return false;
    }

    //TODO I SHOULDNT HAVE TO HAVE THOSE 3 empty methods
    public void loadTexture(Context context){
    }
    public FloatBuffer textCoordBufferGet() {
        return vertexBuffer;
    }
    public int  textureHandleGet() {
        return 1;
    }
    public float[] getColor(){
        return objCoords;
    }
}
