package com.example.bEngine.object;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;

import com.example.bEngine.TextureCoordCalculator;
import com.example.bEngine.service.GlService;
import com.example.bEngine.shader.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static android.opengl.GLES20.*;
/**
 * Created by Ben on 10/10/13.
 */
public abstract class BglObject extends Bobject {

    public GlService glService;

    protected boolean visible = true;
    protected boolean disregardCam = false;
    protected boolean collide = true;
    protected int[] res;

    public BglObject( float x, float y, float w, float h ){
        super(x,y,w,h);
    }


    public int[] getRes() {
        return res;
    }
    public void setRes(int[] res) {
        this.res = res;
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


    public int getTextureState(){
        return 0;
    }

    //TODO is it right or wrong?
    public abstract float[] getColor();
    public abstract void collision();
}
