package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.SpriteSheet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Ben on 04-Jun-14.
 */
public class TextureCoordCalculator {

    public static float textcoords[] = {
        0.0f, 1.0f, 0.0f, // bottom left
        0.0f, 0.0f, 0.0f, // top left
        1.0f, 1.0f, 0.0f, // bottom right

        1.0f, 1.0f, 0.0f, // bottom right
        1.0f, 0.0f, 0.0f, // top right
        0.0f, 0.0f, 0.0f // top left
    };

    public static float[] calculate(int Nx, int Ny){

        int FLOAT_PER_ROW = Nx * 18;
        float [] textCoords = new float[FLOAT_PER_ROW*Ny];
        for (int row=0; row<Ny; row++) {
            for (int col=0; col<Nx; col++) {

                float left = (float) col / Nx;
                float right = (float) (col + 1) / Nx;
                float top = (float) row / Ny;
                float bottom = (float) (row + 1) / Ny;

                int offset = col * 18 + row*FLOAT_PER_ROW;

                //bottom left
                textCoords[0 + offset] = left;
                textCoords[1 + offset] = bottom;
                textCoords[2 + offset] = 0;
                //top left
                textCoords[3 + offset] = left;
                textCoords[4 + offset] = top;
                textCoords[5 + offset] = 0;
                //bottom right
                textCoords[6 + offset] = right;
                textCoords[7 + offset] = bottom;
                textCoords[8 + offset] = 0;

                //bottom right
                textCoords[9 + offset] = textCoords[6 + offset];
                textCoords[10 + offset] = textCoords[7 + offset];
                textCoords[11 + offset] = 0;
                //top right
                textCoords[12 + offset] = right;
                textCoords[13 + offset] = top;
                textCoords[14 + offset] = 0;
                //top left
                textCoords[15 + offset] = textCoords[3 + offset];
                textCoords[16 + offset] = textCoords[4 + offset];
                textCoords[17 + offset] = 0;
            }
        }
        return textCoords;
    }

    public static float[][][] calculate( SpriteSheet[] spritesheet) {

        int[] number_of_frame = new int[spritesheet.length];
        int[] number_of_frame_real = new int[spritesheet.length];

        float[][][] textcoords = new float [ spritesheet.length ][ ][ ];

        /* to increase the size of texturehandle[] when more than one texture */
        //textureHandle = new int[spritesheet.length];

        for (int i=0; i<spritesheet.length; i++){

            SpriteSheet obj = spritesheet[i];

            float xFrame = 1.0f / obj.getNumber_of_frame_x();
            float yFrame = 1.0f / obj.getNumber_of_frame_y();

            number_of_frame[i] = obj.getNumber_of_frame_x() * obj.getNumber_of_frame_y();
            number_of_frame_real[i] = obj.getNumber_of_frame_real();

            textcoords[i] = new float [ number_of_frame_real[i] ][ ];
            int v=0;
            int spriteFrame = 0;
            for (int j=0; j < obj.getNumber_of_frame_y(); j++){
                for (int ii=0; ii < obj.getNumber_of_frame_x(); ii++){

                    textcoords[i][spriteFrame] = new float [18];
                    float [] coords = textcoords[i][spriteFrame];

                    coords[0] = ii*xFrame;
                    coords[1] = (j+1.0f)*yFrame;
                    coords[2] = 0.0f;

                    coords[3] = ii*xFrame;
                    coords[4] = yFrame*j;
                    coords[5] = 0.0f;

                    coords[6] = xFrame*(1.0f+ii);
                    coords[7] = (j+1.0f)*yFrame;
                    coords[8] = 0.0f;

                    coords[9] = xFrame*(1.0f+ii);
                    coords[10] = (j+1.0f)*yFrame;
                    coords[11] = 0.0f;

                    coords[12] = xFrame*(1.0f+ii);
                    coords[13] = yFrame*j;
                    coords[14] = 0.0f;

                    coords[15] = ii*xFrame;
                    coords[16] = yFrame*j;
                    coords[17] = 0.0f;

                    spriteFrame++;
                }
            }

        }
        return textcoords;
    }

}
