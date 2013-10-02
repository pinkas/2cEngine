package com.example.BGL;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ben on 9/26/13.
 */
public class AnimatedObject  extends BglObject {

    private final int [] number_of_frame;
    private final int [] number_of_frame_real;
    private int state;
    //TODO rename inner_state
    private SpriteSheet[] spriteSheet;
    private int inner_state = 0;
    private boolean blah=false;

    //TODO are you serious ??????
    private final int [] textureHandle2 = new int [2];


    public AnimatedObject(int x, int y, int w, int h, SpriteSheet[] spritesheet) {

        super(x, y, w, h, spritesheet[0].getTexture_id());

        this.spriteSheet = spritesheet;

        textCoordBuffer = new FloatBuffer [spritesheet.length];

        number_of_frame = new int[spritesheet.length];
        number_of_frame_real = new int[spritesheet.length];

        float[][] textcoords = new float [ spritesheet.length ][ ];

        for (int i=0; i<spritesheet.length; i++){

            SpriteSheet obj = spritesheet[i];

            float w_offset = (float) w / obj.getNumber_of_frame_x();
            float h_offset = (float) h / obj.getNumber_of_frame_y();

            number_of_frame[i] = obj.getNumber_of_frame_x() * obj.getNumber_of_frame_y();
            number_of_frame_real[i] = obj.getNumber_of_frame_real();

            this.state = 0;

            textcoords[i] = new float [ 12*number_of_frame[i] ];
            int v=0;

            for (int j=0; j < obj.getNumber_of_frame_y();j++){
                for (int ii=0; ii < obj.getNumber_of_frame_x();ii++){

                    textcoords[i][v] = ii*(w_offset/w)+0.0f;
                    textcoords[i][v+1] = (h_offset/h)*(0.0f+j);

                    textcoords[i][v+2] = 0.0f;
                    textcoords[i][v+3] = ii*(w_offset/w)+0.0f;

                    textcoords[i][v+4] = (j+1)*(h_offset/h);
                    textcoords[i][v+5] = 0.0f;

                    textcoords[i][v+6] = (w_offset/w)*(1.0f+ii);
                    textcoords[i][v+7] = (j+1)*(h_offset/h);

                    textcoords[i][v+8] = 0.0f;
                    textcoords[i][v+9] = (w_offset/w)*(1.0f+ii);

                    textcoords[i][v+10] = (h_offset/h)*(0.0f+j);
                    textcoords[i][v+11] = 0.0f;

                    v = v + 12;

                }
            }


            ByteBuffer bb = ByteBuffer.allocateDirect(textcoords[i].length * 4 * number_of_frame[i]);
            bb.order(ByteOrder.nativeOrder());

            textCoordBuffer[i] = bb.asFloatBuffer();
            textCoordBuffer[i].put(textcoords[i]);
            textCoordBuffer[i].position(0);
        }
    }

    public void animate (){

        if (state >= number_of_frame_real[inner_state] ){
                state = 0;
        }
        textCoordBuffer[inner_state].position(state * 12);
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

    // Finger touches the object
    public void touchDown() {
        inner_state = 1;
    }
    // Finger realeases when over the object
    public void touchUp() {
        inner_state = 0;
    }    //When moving your finger over the object
    public void touchMove() {
    }
    // Triggered when releasing the finger and it's not over the object anymore
    public void touchUpMove(int x, int y){
        inner_state = 0;
    }

    public FloatBuffer textCoordBufferGet() {
        return textCoordBuffer[inner_state];
    }

    public int  textureHandleGet() {
        if (inner_state == 0)
            return textureHandle[0];
        else
            return textureHandle2[0];
    }

    public void loadTexture(Context context){
        //create a bitmap, from image to pixel data, has to be done whenever we reload the
        //texture since we "recycle" the bitmap at the end
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        for ( int i=0; i< spriteSheet.length; i++ ){
            bitmap = BitmapFactory.decodeResource(context.getResources(), spriteSheet[i].getTexture_id(), options);
            if (i==0)
                mShader.loadTexture(bitmap, textureHandle);
            else
                mShader.loadTexture(bitmap, textureHandle2);
        }
        bitmap.recycle();
    }


}