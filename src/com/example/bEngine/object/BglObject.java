package com.example.bEngine.object;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;

import com.example.bEngine.shader.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ben on 10/10/13.
 */
public abstract class BglObject extends Bobject {

    protected String shaderName;
    protected boolean boundToCamera;
    protected PointF offsetCamera;

    private final FloatBuffer vertexBuffer;

    private float objCoords[] = {
            -1, 1, 0,    // top left
            -1, -1, 0,   // bottom left
             1, -1, 0,   // bottom right
             1,  1, 0 }; // top right



    public BglObject( float x, float y, float w, float h ){
        super(x,y,w,h);

        ByteBuffer bb = ByteBuffer.allocateDirect( objCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(objCoords);
        vertexBuffer.position(0);

        shaderName = "basic";
    }

    public void setShader(String shaderName){
        this.shaderName = shaderName;
    }

    public String getShaderName(){
        return shaderName;
    }

    public FloatBuffer vertexBufferGet() {
        return vertexBuffer;
    }

    public void draw( float[] mat, Shader shader ) {
        GLES20.glUseProgram(shader.get_program());
        shader.sendParametersToShader(this, mat);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }

    public boolean getBoundToCamera (){
        return boundToCamera;
    }

    public  void setOffsetCamera( PointF offset ){
        offsetCamera = offset;
    }

    public PointF getCameraOffset(){
        return offsetCamera;
    }

    public void setBoundToCamera (boolean bound){
        if (!bound){
            offsetCamera = null;
        }
        boundToCamera = bound ;
    }


    public FloatBuffer textCoordBufferGet() {
        return vertexBuffer;
    }

    //TODO is it right or wrong?
    public abstract int  textureHandleGet();
    public abstract float[] getColor();
}
