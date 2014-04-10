package com.example.bEngine;

/**
 * Created by Ben on 10/21/13.
 */
public class Angle {

    public float x;
    public float y;
    public float z;

    public Angle(){
        x=0;
        y=0;
        z=0;
    }

    public void setX (float angleX){
        x = angleX;
    }
    public void setY (float angleY){
        y = angleY;
    }
    public void setZ (float angleZ){
        z = angleZ;
    }

    public float getX (){
       return x;
    }
    public float getY (){
        return  y;
    }
    public float getZ (){
        return z;
    }

}
