package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.SpriteSheet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ben on 04-Jun-14.
 */
public class TextureCoordCalculator {

    private static float textcoords[] = {
        0.0f, 1.0f, 0.0f, // bottom left
        0.0f, 0.0f, 0.0f, // top left
        1.0f, 1.0f, 0.0f, // bottom right
        1.0f, 0.0f, 0.0f, // top right
    };

    public static FloatBuffer[] calculate(float x, float y, float w, float h, SpriteSheet[] spritesheet) {


        FloatBuffer[] textCoordBuffer = new FloatBuffer[spritesheet.length];

        int[] number_of_frame = new int[spritesheet.length];
        int[] number_of_frame_real = new int[spritesheet.length];

        float[][] textcoords = new float [ spritesheet.length ][ ];

        /* to increase the size of texturehandle[] when more than one texture */
        //textureHandle = new int[spritesheet.length];

        for (int i=0; i<spritesheet.length; i++){

            SpriteSheet obj = spritesheet[i];

            float xFrame = 1.0f / obj.getNumber_of_frame_x();
            float yFrame = 1.0f / obj.getNumber_of_frame_y();

            number_of_frame[i] = obj.getNumber_of_frame_x() * obj.getNumber_of_frame_y();
            number_of_frame_real[i] = obj.getNumber_of_frame_real();

            textcoords[i] = new float [ 12*number_of_frame[i] ];
            int v=0;

            for (int j=0; j < obj.getNumber_of_frame_y(); j++){
                for (int ii=0; ii < obj.getNumber_of_frame_x(); ii++){

                    textcoords[i][v] = ii*xFrame;
                    textcoords[i][v+1] = (j+1.0f)*yFrame;
                    textcoords[i][v+2] = 0.0f;

                    textcoords[i][v+3] = ii*xFrame;
                    textcoords[i][v+4] = yFrame*j;
                    textcoords[i][v+5] = 0.0f;

                    textcoords[i][v+6] = xFrame*(1.0f+ii);
                    textcoords[i][v+7] = (j+1.0f)*yFrame;
                    textcoords[i][v+8] = 0.0f;

                    textcoords[i][v+9] = xFrame*(1.0f+ii);
                    textcoords[i][v+10] = yFrame*j;
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
        return textCoordBuffer;
    }

    public static FloatBuffer[] calculate(){
        ByteBuffer bb = ByteBuffer.allocateDirect(textcoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer[] textCoordBuffer = new FloatBuffer[1];
        textCoordBuffer[0] = bb.asFloatBuffer();
        textCoordBuffer[0].put(textcoords);
        textCoordBuffer[0].position(0);
        return textCoordBuffer;
    }

    public static FloatBuffer[] calculate( int x, int y, int tesselX, int tesselY){

        float [] textcoords = new float[12];

        textcoords[0] = x*(1.0f/tesselX);
        textcoords[1] = (y+1)*(1.0f/tesselY);
        textcoords[2] = 0.0f;

        textcoords[3] = x*(1f/tesselX);
        textcoords[4] = (1f/tesselY)*(0.0f+y);
        textcoords[5] = 0.0f;

        textcoords[6] = (1f/tesselX)*(1.0f+x);
        textcoords[7] = (y+1)*(1f/tesselY);
        textcoords[8] = 0.0f;

        textcoords[9] = (1f/tesselX)*(1.0f+x);
        textcoords[10] = (1f/tesselY)*(0.0f+y);
        textcoords[11] = 0.0f;


        ByteBuffer bb = ByteBuffer.allocateDirect(textcoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer[] textCoordBuffer = new FloatBuffer[1];
        textCoordBuffer[0] = bb.asFloatBuffer();
        textCoordBuffer[0].put(textcoords);
        textCoordBuffer[0].position(0);
        return textCoordBuffer;
    }

}
