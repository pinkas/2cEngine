package com.example.bEngine.object;

/**
 * Created by Ben on 10/12/13.
 */
public class SpriteSheet {

    private int texture_id;
    private int number_of_frame_x;
    private int number_of_frame_y;
    private int number_of_frame_real;

    public SpriteSheet(int texture_id, int number_of_frame_x, int number_of_frame_y, int number_of_frame_real){
        this.texture_id = texture_id;
        this.number_of_frame_x = number_of_frame_x;
        this.number_of_frame_y = number_of_frame_y;
        this.number_of_frame_real = number_of_frame_real;
    }

    public int getTexture_id() {
        return texture_id;
    }

    public int getNumber_of_frame_x() {
        return number_of_frame_x;
    }

    public int getNumber_of_frame_y() {
        return number_of_frame_y;
    }

    public int getNumber_of_frame_real() {
        return number_of_frame_real;
    }
}
