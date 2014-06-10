package com.example.bEngine.object;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;

import com.example.bEngine.TextureCoordCalculator;
import com.example.bEngine.shader.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static android.opengl.GLES20.*;
/**
 * Created by Ben on 10/10/13.
 */
public abstract class BglObject extends Bobject {

    protected String shaderName;
    protected boolean boundToCamera;
    protected PointF offsetCamera;
    protected boolean visible = true;
    protected boolean disregardCam = false;
    //TODO Should alpha be delcared here? why the layer or other gl stuff aren't declared here??
    protected float alpha = 1.0f;
    protected boolean collide = true;

    //TODO get rid of that!!!!!
    private FloatBuffer[] textCoordBuffer;


    public BglObject( float x, float y, float w, float h ){
        super(x,y,w,h);
        shaderName = "basic";
        textCoordBuffer = TextureCoordCalculator.calculate();
    }

    public void setShader(String shaderName){
        this.shaderName = shaderName;
    }

    public String getShaderName(){
        return shaderName;
    }


    public int getLayer() {
        return layer;
    }
    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setVisible( boolean visible ){
        this.visible = visible;
    }
    public boolean isVisible(){
        return visible;
    }

    public void setAlpha( float alpha ){
        this.alpha = alpha;
    }
    public float getAlpha(){
        return alpha;
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

    public boolean getDisregardCam(){
        return disregardCam;
    }

    public void setDisregardCam( boolean cam ){
        disregardCam = cam;
    }

    //TODO is it bad design to have to set mannually if an object is concerne par les collisions
    public boolean isCollide() {
        return collide;
    }

    public void setCollide(boolean collide) {
        this.collide = collide;
    }

    //TODO get rid of that!!!! shouldnt be here!!!!
    public FloatBuffer textCoordBufferGet() {
        return textCoordBuffer[0];
    }

    public int getTextureState(){
        return 0;
    }

    //TODO is it right or wrong?
    public abstract int  textureHandleGet();
    public abstract void setTextureHandle();
    public abstract float[] getColor();
    public abstract void collision();
}
