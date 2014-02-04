package com.example.bEngine.object;

import android.graphics.PointF;
import android.graphics.Rect;

import com.example.bEngine.Angle;

/**
 * Created by Ben on 10/9/13.
 */
public class Bobject {

    protected PointF pos;
    private float z;
    protected PointF anchor;
    protected Angle angle = new Angle();
    protected PointF size;
    protected boolean pressed;

    public Bobject( float x, float y, float w, float h){
        pos = new PointF(x,y);
        size = new PointF(w,h);
        anchor = new PointF (0.0f, 0.0f);
        z = 0;
    }

    public Bobject(){
        pos = new PointF(0,0);
        size = new PointF(0,0);
        anchor = new PointF (0.0f, 0.0f);
        z = 0;
    }

    public void anchorPointSet( float x, float y ) {
        anchor.x = x;
        anchor.y = y;
    }

    public PointF anchorPointGet() {
        return anchor;
    }

    public void resize(float w, float h){
        //TODO  check if parameters are correct?
        size.x = w;
        size.y = h;
    }

    // Finger touches the object
    public void touchDown() throws Exception {
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

    public PointF sizeGet() {
        return size;
    }

    public PointF posGet () {
        return pos;
    }

    public void zSet(float z) {
        this.z = z;
    }

    public float zGet() {
        return z;
    }

    public void posSet ( PointF pos ) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }
    public void posSet ( float x, float y ) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public void update(){
      //  rectangle.set(pos.x, pos.y, pos.x + w, pos.y + h);
    }

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
}
