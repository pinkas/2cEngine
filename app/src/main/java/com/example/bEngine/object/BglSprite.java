package com.example.bEngine.object;

import com.example.bEngine.scene.SceneManager;
import com.example.bEngine.service.CollisionService;
import com.example.bEngine.service.GlService;


public class BglSprite extends BglObject {


    public BglSprite( float x, float y, float w, float h){
        super(x,y,w,h);
        glService = new GlService();
    }

    public BglSprite( float x, float y, float w, float h, int[] res ){
        super(x,y,w,h);
        this.res = res;
        glService = new GlService();
	}



    @Override
    //TODO because Brectangle has a getColor ....
    public float[] getColor(){
        float color[] = new float[4];
        return color;
    }

    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs){}

}
