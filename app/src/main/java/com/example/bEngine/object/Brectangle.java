package com.example.bEngine.object;

import android.content.Context;

import com.example.bEngine.service.Gl;
import com.example.bEngine.service.GlService;
import com.example.bEngine.shader.Shader;

/**
 * Created by Ben on 10/9/13.
 */
public class Brectangle extends BglObject {

    private float color[];

    public Brectangle( float x, float y, float w, float h, float r, float g, float b, float a){
        super(x,y,w,h);
        glService = new GlService();
        glService.setShaderName("rect");
        color = new float[4];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
    }

    public Brectangle( float x, float y, float w, float h, float r, float g, float b){

        super(x,y,w,h);
        glService =new GlService();

        glService.setShaderName("rect");
        color = new float[4];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = 1.0f;
    }

    public Brectangle(){
        super(0,0,0.3f,0.3f);
        glService =new GlService();

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


    @Override
    public void collision(){}

}
