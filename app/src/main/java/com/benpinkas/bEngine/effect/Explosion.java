package com.benpinkas.bEngine.effect;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.object.Btimer;
import java.util.concurrent.Callable;


public class Explosion {

    private int progress;
    private BglObject exploder;
    private float[] velocity;
    private float[] offset;
    private int tesselX, tesselY;


    public Explosion(BglObject exploder, int tesselX, int tesselY){
        this.exploder = exploder;
        this.tesselX = tesselX;
        this.tesselY = tesselY;
        // 2 car une composante x et une y pour la traj
        this.velocity = new float[tesselX*tesselY*2];
        this.offset = new float[tesselX*tesselY*2];

        for(int i=0; i+1<velocity.length; i = i + 2){

            float initialVelx = 0.01f;
            float initialVely = 0.015f;

            int row = i/(tesselX*2); // It's like flooring
            int col = i - row * (tesselX*2);

            //goes from -1 to 1 where -1 is left, 1 the extreme right of the explo
            float velxCoeff = ( (float) col /  ((tesselX-1) * 2f) - 0.5f) * 2f ;
            //goes from 0 (bottom) to 1 (top)
            float velyCoeff = Math.abs( (float) row / (tesselY-1) - 1 ) ;
            velocity[i] = velxCoeff*initialVelx;
            velocity[i+1] = initialVely*velyCoeff + 0.012f;
            offset[i] = 0;
            offset[i+1] = 0;
        }
    }

    // TODO explosion should inerit from a class that has an update method? or interface.
    // Thanks to that the update method would be automatically called, just like objects
    public void update(){
       progress ++;
        for(int i=0;i+1<velocity.length;){

            offset[i] = (velocity[i] * progress)/10f;
            offset[i+1] = (velocity[i+1] * progress)/6f;

        //    velocity[i] = velocity[i] - velocity[i]/50f;
            velocity[i+1] = velocity[i+1] - 0.0004f;


            i = i + 2;
        }

        float opacity = exploder.glService.getAlpha();
        exploder.glService.setAlpha(opacity-0.015f);

        exploder.glService.setExtraVertexData(offset);
    }

    public void reset(){
        progress = 0;
        exploder.glService.setShaderName("textureShader");
        exploder.glService.setAlpha(1);
        exploder.glService.tesselate(1, 1);
    }

    public void start(float dirx, float diry){
        exploder.glService.tesselate(tesselX, tesselY);
        exploder.glService.setShaderName("explosion");

        new Btimer(1, new Callable() {
            @Override
            public Boolean call() {
                update();

                if ( progress > 70 ){
                    reset();
                    exploder.setVisible(false);
                 //   MessageManager.sendMessage("explosion_end");
                    return false;
                }
                return true;
            }
        } );
    }
}
