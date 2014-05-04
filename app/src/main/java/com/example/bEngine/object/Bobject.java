package com.example.bEngine.object;

import android.graphics.PointF;
import android.graphics.Rect;

import com.example.bEngine.Angle;

import com.google.common.base.Preconditions;

/**
 * Created by Ben on 10/9/13.
 */
public class Bobject {

    protected PointF pos;
    protected Angle angle = new Angle();
    protected PointF size;
    protected float z;

    protected int layer;
    protected PointF anchor;
    protected boolean pressed;
    protected boolean toDelete = false;

    public Bobject( float x, float y, float w, float h){

        Preconditions.checkArgument( w > 0 && h > 0, "Size of an object should be greater than 0");

        pos = new PointF(x,y);
        size = new PointF(w,h);
        anchor = new PointF (0.0f, 0.0f);
        z = 0;
        layer = 0;
    }

    public void anchorPointSet( float x, float y ) {
        anchor.x = x;
        anchor.y = y;
    }

    public PointF anchorPointGet() {
        return anchor;
    }

    public void setSize(float w, float h){
        Preconditions.checkArgument( w > 0 && h > 0,
                "Size of an object should be greater than 0");
        size.x = w;
        size.y = h;
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

    public PointF getSize() {
        return size;
    }

    public PointF getPos () {
        return pos;
    }

    public void zSet(float z) {
        this.z = z;
    }

    public float zGet() {
        return z;
    }

    public void setPos ( PointF pos ) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }
    public void setPos ( float x, float y ) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public void update(){}

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
