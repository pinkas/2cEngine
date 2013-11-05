package com.example.bEngine.object;

/**
 * Created by Ben on 10/9/13.
 */
public class Brectangle extends BglObject {

    private float color[];

    public Brectangle( float x, float y, float w, float h, float r, float g, float b, float a){

        super(x,y,w,h);

        color = new float[4];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;

    }

    public float[] getColor(){
        return color;
    }

    public void setColor( float r, float g, float b, float a ){
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
    }
}
