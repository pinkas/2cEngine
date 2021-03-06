package com.benpinkas.bEngine.object;

import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.GlService;

/**
 * Created by Ben on 10/9/13.
 */
public class Brectangle extends BglObject {

    protected float color[];

    public Brectangle(float x, float y, float w, float h, float r, float g, float b, float a){
        super(x,y,w,h);
        glService = new GlService();
        glService.setShaderName("rect");
        color = new float[4];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;

        name = "rectangle";
    }

    public Brectangle(float x, float y, float w, float h, float r, float g, float b){

        super(x,y,w,h);
        glService = new GlService();

        glService.setShaderName("rect");
        color = new float[4];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = 1.0f;
    }

    public Brectangle(){
        super(0,0,0.3f,0.3f);
        glService = new GlService();

        glService.setShaderName("rect");
        color = new float[4];
        color[0] = 0.8f;
        color[1] = 0.5f;
        color[2] = 0.5f;
        color[3] = 1.0f;
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

    public void setColor(Brectangle r){
        color[0] = r.color[0];
        color[1] = r.color[1];
        color[2] = r.color[2];
        color[3] = r.color[3];
    }

    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs){}

}
