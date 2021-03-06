package com.benpinkas.bEngine.object;

import android.graphics.PointF;

import com.benpinkas.bEngine.Angle;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Ben on 10/9/13.
 */
public class Bobject {

    public static final int LAYER_MAX = 10;

    private float x;
    private float y;
    private float w;
    private float h;
    protected PointF vel;
    protected float z;
    protected Angle angle = new Angle();

    private Callable<Void> touchU;
    private Callable<Void> touchD;
    private Callable<Void> touchM;

    protected int layer;
    protected boolean pressed;
    protected boolean toDelete = false;

    private ArrayList<Updatable> updatables = new ArrayList<Updatable>();

    protected String name = "defaultName";

    public Bobject( float x, float y, float w, float h){

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
        if (touchU != null ){
            try {
                touchU.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // Finger realeases when over the object
    public void touchUp() {
        pressed = false;
        if (touchD != null ){
            try {
                touchD.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //When moving your finger over the object
    public void touchMove(float x, float y) {
        if(touchM != null){
            try{
                touchM.call();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    // Triggered when releasing the finger when not over the object anymore
    public void touchUpMove(float x, float y){
        pressed = false;
    }

    public void setTouchU( Callable<Void> cb ){
        touchU = cb;
    }
    public void setTouchD( Callable<Void> cb ){
        touchD = cb;
    }
    public void setTouchM( Callable<Void> cb ) { touchM = cb; }

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
        this.w = w;
        this.h = h;
    }

    public float getPosX (){
        return x;
    }
    public float getPosX (float centerPointX){
        return x + w * centerPointX;
    }
    public float getPosY (){
        return y;
    }
    public float getPosY (float centerPointY){
        return y + h * centerPointY;
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
    public void setPos(Bobject o, float dx, float dy){
        this.x = o.x + dx;
        this.y = o.y + dy;
    }
    /** Position the object according to x & y and an anchor point oriX & oriY
     * 0,0 being the center of the object -0.5, 1 bottom left */
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

    public void addUpdatable(Updatable o) {
        updatables.add(o);
    }

    public ArrayList<Updatable> getUpdatable() {
        return updatables;
    }

    public String getName() {
        return name;
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
