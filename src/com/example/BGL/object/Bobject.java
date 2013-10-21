package com.example.BGL.object;

import android.graphics.Point;
import android.graphics.Rect;

import com.example.BGL.utils.Angle;

/**
 * Created by Ben on 10/9/13.
 */
public class Bobject {

    protected Point pos;
    private float z;
    protected Point anchor;
    protected Angle angle = new Angle();
    protected int w;
    protected int h;
    public Rect rectangle;
    protected boolean pressed;

    public Bobject(int x, int y, int w, int h){

        pos = new Point(x,y);
        anchor = new Point (w/2, h/2);
        z = 0;

        this.w = w;
        this.h = h;

        rectangle = new Rect( pos.x, pos.y, pos.x + w, pos.y + h );
    }


    public void anchorPointSet( float x, float y ) {
        this.anchor.x =  (int) (w/2 - (x*w));
        this.anchor.y =  (int) (h/2 - (y*h));
    }

    public Point anchorPointGet() {
        return anchor;
    }

    public void resize(int w, int h){
        //TODO  check if parameters are correct?
        this.w = w;
        this.h = h;

        rectangle.set(pos.x, pos.y, pos.x + this.w, pos.y + this.h);
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
    public void touchMove(int x, int y) {
    }
    // Triggered when releasing the finger and it's not over the object anymore
    public void touchUpMove(int x, int y){
        pressed = false;
    }

    public boolean isPressed(){
        return pressed;
    }

    public int widthGet() {
        return  w;
    }

    public int heightGet() {
        return  h;
    }

    public Point posGet () {
        return pos;
    }

    public void zSet() {
        this.z += 0.005;
    }

    public float zGet() {
        return z;
    }

    public void posSet ( Point pos ) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        rectangle.set(pos.x, pos.y, pos.x + w, pos.y + h);
    }
    public void posSet ( int x, int y ) {
        this.pos.x = x;
        this.pos.y = y;
        rectangle.set(pos.x, pos.y, pos.x + w, pos.y + h);
    }

    public void update(){
        rectangle.set(pos.x, pos.y, pos.x + w, pos.y + h);
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
