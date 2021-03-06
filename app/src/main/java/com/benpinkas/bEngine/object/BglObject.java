package com.benpinkas.bEngine.object;

import android.graphics.PointF;

import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.BtextureManager;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.GlService;

import java.util.List;

/**
 * Created by Ben on 10/10/13.
 */
public abstract class BglObject extends Bobject {

    public GlService glService;
    public CollisionService collisionService = new CollisionService();

    protected int[] res;

    public boolean visible = true;
    protected boolean disregardCam = false;
    public boolean collide = false;
    public boolean collideFixPos = true;
    protected List areaOfInterest;

    protected float[] mvp = new float[16];
    protected float[] modelMat = new float[16];

    private boolean dirty = true;

    public BglObject( float x, float y, float w, float h ){
        super(x,y,w,h);
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
    public void setPos(Bobject o,float dx, float dy) {
        super.setPos(o, dx, dy);
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
    public void setSize(float w) {
        float aspectRatio = (float) getTextureWidth()/ (float) getTextureHeight();
        super.setSize(w, (w/aspectRatio)*(Brenderer.getScreenAspectRatio()));
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
    public float[] getModelMat() {
        return  modelMat;
    }

    public boolean isDirty() {
        return dirty;
    }
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
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

    public boolean isCollideFixPos() {
        return collideFixPos;
    }
    public void setCollideFixPos(boolean collideFixPos) {
        this.collideFixPos = collideFixPos;
    }

    public List getAreaOfInterest() {
        return areaOfInterest;
    }
    public void setAreaOfInterest(List areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    public int getTextureWidth(){
        return BtextureManager.getTextureDimensions(res[0]).x;
    }

    public int getTextureHeight(){
        return BtextureManager.getTextureDimensions(res[0]).y;
    }

    //TODO is it right or wrong?
    public abstract float[] getColor();
    public abstract void collision(Bobject collider, CollisionService.collisionSide colSide);

}
