package com.example.bEngine.object;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.bEngine.Brenderer;
import com.example.bEngine.TextureCoordCalculator;
import com.example.bEngine.scene.SceneManager;
import com.example.bEngine.service.CollisionService;
import com.example.bEngine.service.GlService;
import com.example.bEngine.shader.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import static android.opengl.GLES20.*;
/**
 * Created by Ben on 10/10/13.
 */
public abstract class BglObject extends Bobject {

    public GlService glService;
    public CollisionService collisionService = new CollisionService();
    protected int[] res;

    public boolean visible = true;
    protected boolean disregardCam = false;
    public boolean collide = true;
    protected List areaOfInterest;

    protected float[] mvp = new float[16];
    public boolean dirty;

    public BglObject( float x, float y, float w, float h ){
        super(x,y,w,h);
        dirty = true;
    }

    @Override
    public void setPos(PointF pos) {
        super.setPos(pos);
        dirty = true;
    }
    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        dirty = true;
    }
    @Override
    public void setPos(Bobject o) {
        super.setPos(o);
        dirty = true;
    }
    @Override
    public void setPos(float x, float y, float oriX, float oriY) {
        super.setPos(x, y, oriX, oriY);
        dirty = true;
    }
    @Override
    public void setZ(float z) {
        super.setZ(z);
        dirty = true;
    }
    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        dirty = true;
    }
    @Override
    public void setAngleX(float anglex) {
        super.setAngleX(anglex);
        dirty = true;
    }
    @Override
    public void setAngleY(float angley) {
        super.setAngleY(angley);
        dirty = true;
    }
    @Override
    public void setAngleZ(float anglez) {
        super.setAngleZ(anglez);
        dirty = true;
    }

    public float[] getMvp() {
        return mvp;
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

    public boolean isCollide() {
        return collide;
    }
    public void setCollide(boolean collide) {
        this.collide = collide;
    }

    public List getAreaOfInterest() {
        return areaOfInterest;
    }
    public void setAreaOfInterest(List areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    //TODO is it right or wrong?
    public abstract float[] getColor();
    public abstract void collision(Bobject collider, CollisionService.collisionSide colSide);

}
