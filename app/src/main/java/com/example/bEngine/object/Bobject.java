package com.example.bEngine.object;

import android.graphics.PointF;
import android.graphics.Rect;

import com.example.bEngine.Angle;

import com.google.common.base.Preconditions;

/**
 * Created by Ben on 10/9/13.
 */
public class Bobject {

    private float x;
    private float y;
    private float w;
    private float h;
    protected PointF vel;
    protected float z;
    protected Angle angle = new Angle();

    protected int layer;
    protected boolean pressed;
    protected boolean toDelete = false;

    public Bobject( float x, float y, float w, float h){

        Preconditions.checkArgument( w > 0 && h > 0,
                "Size of an object should be greater than 0");

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        vel = new PointF(0,0);
        z = 0;
        layer = 0;
    }

    // Finger touches the object
    public void touchDown() {
        pressed = true;
    }
    // Finger realeases when over the object
    public void touchUp() {
        pressed = false;
    }
    //When moving your finger over the object
    public void touchMove(float x, float y) {
    }
    // Triggered when releasing the finger and it's not over the object anymore
    public void touchUpMove(float x, float y){
        pressed = false;
    }

    public boolean isPressed(){
        return pressed;
    }

    public void setZ(float z) {
        this.z = z;
    }
    public float getZ() {
        return z;
    }

    public float getSizeW() {
        return w;
    }
    public float getSizeH(){
        return h;
    }

    public void setSize(float w, float h){
        Preconditions.checkArgument( w > 0 && h > 0,
                "Size of an object should be greater than 0");
        this.w = w;
        this.h = h;
    }

    public float getPosX (){
        return x;
    }
    public float getPosY (){
        return y;
    }

    public void setPos ( PointF pos ) {
        this.x = pos.x;
        this.y = pos.y;
    }
    public void setPos ( float x, float y ) {
        this.x = x;
        this.y = y;
    }
    public void setPos(Bobject o){
        this.x = o.x;
        this.y = o.y;
    }
    public void setPos( float x, float y, float oriX, float oriY ){
        this.x = x + (0.5f - oriX) * w;
        this.y = y + (0.5f - oriY) * h;
    }

    public PointF getVel() {
        return vel;
    }
    public void setVel(PointF vel) {
        this.vel = vel;
    }
    public void setVel(float x, float y){
        vel.x = x;
        vel.y = y;
    }

    public void update( float dt ){}

    public void setAngleX(float anglex){
        angle.setX(anglex);
    }
    public void setAngleY(float angley){
        angle.setY(angley);
    }
    public void setAngleZ(float anglez){
        angle.setZ(anglez);
    }
    public float getAngleX() {
        return angle.getX();
    }
    public float getAngleY() {
        return angle.getY();
    }
    public float getAngleZ() {
        return angle.getZ();
    }

    public boolean isToDelete() {
        return toDelete;
    }
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }
}
