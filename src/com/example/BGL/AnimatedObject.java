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
    private final int number_of_frame_real;
    private int state;
    private boolean blah=false;

    public AnimatedObject(int x, int y, int w, int h, int texture_id,
                          int number_of_frame_x, int number_of_frame_y, int number_of_frame_real) {

        super(x, y, w, h, texture_id);

        this.w_offset = (float) w / number_of_frame_x;
        this.h_offset = (float) h / number_of_frame_y;

        this.number_of_frame = number_of_frame_x * number_of_frame_y;
        this.number_of_frame_real = number_of_frame_real;

        this.state = 0;

        float[] textcoords = new float [12*number_of_frame];
        int v=0;

        for (int j=0; j < number_of_frame_y;j++){
            for (int i=0; i < number_of_frame_x;i++){

                textcoords[v] = i*(w_offset/w)+0.0f;
                textcoords[v+1] = (h_offset/h)*(0.0f+j);

                textcoords[v+2] = 0.0f;
                textcoords[v+3] = i*(w_offset/w)+0.0f;
                textcoords[v+4] = (j+1)*(h_offset/h);

                textcoords[v+5] = 0.0f;
                textcoords[v+6] = (w_offset/w)*(1.0f+i);
                textcoords[v+7] = (j+1)*(h_offset/h);

                textcoords[v+8] = 0.0f;
                textcoords[v+9] = (w_offset/w)*(1.0f+i);
                textcoords[v+10] = (h_offset/h)*(0.0f+j);

                textcoords[v+11] = 0.0f;

                v = v + 12;

            }
        };


        ByteBuffer bb = ByteBuffer.allocateDirect(textcoords.length * 4 * number_of_frame);
        bb.order(ByteOrder.nativeOrder());
        textCoordBuffer = bb.asFloatBuffer();
        textCoordBuffer.put(textcoords);
        textCoordBuffer.position(0);

    }

    public void animate (){

        if (state >= number_of_frame_real ){
                state = 0;
        }
        System.out.println( state*12);
        textCoordBuffer.position(state*12);
        if (blah){
            state ++;
            blah = false;
        }
        else
            blah = true;
    }

    public void update(){
        //super();
        animate();
    }

}