package com.example.bEngine.service;

import android.graphics.PointF;

import com.example.bEngine.object.BglObject;

/**
 * Created by Ben on 25-Jul-14.
 */
public class CollisionService {


    private float x1;
    private float h1;
    private float y1;
    private float dist1;
    private float vecX;
    private float dist2;
    private float vecY;

    public enum collisionSide {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }

    public boolean collide( BglObject obj1, BglObject obj2) {

        x1 = obj1.getPosX();
        y1 = obj1.getPosY();
        float w1 = obj1.getSizeW();
        h1 = obj1.getSizeH();

        float x2 = obj2.getPosX();
        float y2 = obj2.getPosY();

        float w2 = obj2.getSizeW();
        float h2 = obj2.getSizeH();

        
        dist1 = x1 + w1 / 2f - (x2 + w2 / 2f);
        vecX = (w1 + w2) / 2f - Math.abs(dist1);

        if (vecX > 0) {
            dist2 = y1 + h1 / 2f - (y2 + h2 / 2f);
            vecY = (h1 + h2) / 2f - Math.abs(dist2);
            if (vecY > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Fix obj1 and obj2 positions if they collide
     * and trigger their collision methods
     *
     * @param obj1
     * @param obj2
     */
    public void fixPos(BglObject obj1, BglObject obj2){
        if (vecX < vecY){
            if (dist1>0) {
                obj1.setPos(x1 + vecX, y1);
                obj1.collision(obj2, collisionSide.LEFT);
                obj2.collision(obj1, collisionSide.RIGHT);
            }
            else {
                obj1.setPos(x1 - vecX, y1);
                obj1.collision(obj2, collisionSide.RIGHT);
                obj2.collision(obj1, collisionSide.LEFT);
            }
        }else {
            if (dist2>0) {
                obj1.setPos(x1, y1 + vecY);
                obj1.collision(obj2, collisionSide.TOP);
                obj2.collision(obj1, collisionSide.BOTTOM);
            }
            else {
                obj1.setPos(x1, y1 - vecY);
                obj1.collision(obj2, collisionSide.BOTTOM);
                obj2.collision(obj1, collisionSide.TOP);
            }
        }
    }


}
