package com.example.BGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Ben on 9/26/13.
 */
public class AnimatedObject  extends BglObject {

    private final float w_offset;
    private final float h_offset;
    private final int number_of_frame;
    private int state;
    private boolean blah=false;

    public AnimatedObject(int x, int y, int w, int h, int texture_id,
                          float w_offset, float h_offset, int number_of_frame) {

        super(x, y, w, h, texture_id);

        this.w_offset = w_offset;
        this.h_offset = h_offset;
        this.number_of_frame = number_of_frame;

        this.state = 0;

        float[] textcoords = new float [12*number_of_frame];
        int v;
        for (int i=0;i<number_of_frame;i++){
            v = i*12;

            textcoords[v ] = i*(w_offset/w)+0.0f;
            v++;
            textcoords[ v] = 0.0f;
            v++;
            textcoords[ v] = 0.0f;
            v++;
            textcoords[ v ] = i*(w_offset/w)+0.0f;
            v++;
            textcoords[ v ] = 1.0f;
            v++;
            textcoords[v] = 0.0f;
            v++;
            textcoords[v] = (w_offset/w)*(1.0f+i);
            v++;
            textcoords[v] = 1.0f;
            v++;
            textcoords[v] = 0.0f;
            v++;
            textcoords[v] = (w_offset/w)*(1.0f+i);
            v++;
            textcoords[v] = 0.0f;
            v++;
            textcoords[v] = 0.0f;

        };


        ByteBuffer bb = ByteBuffer.allocateDirect(textcoords.length * 4 * number_of_frame);
        bb.order(ByteOrder.nativeOrder());
        textCoordBuffer = bb.asFloatBuffer();
        textCoordBuffer.put(textcoords);
        textCoordBuffer.position(0);

    }

    public void animate (){
        textCoordBuffer.position(state*12);
        if (blah){
            state ++;
            blah = false;
        }
        else
            blah = true;
        if (state > number_of_frame){
                state = 0;
        }
    }

    public void update(){
        //super();
        animate();
    }

}