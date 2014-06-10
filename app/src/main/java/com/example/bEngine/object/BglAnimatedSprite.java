package com.example.bEngine.object;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.bEngine.BtextureManager;
import com.example.bEngine.TextureCoordCalculator;
import com.example.bEngine.shader.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.glGenTextures;

/**
 * Created by Ben on 9/26/13.
 */
public class BglAnimatedSprite  extends BglSprite {

    private final int [] number_of_frame_real;
    private int state;
    //TODO rename inner_state
    private SpriteSheet[] spriteSheet;
    private int inner_state = 0;
    private boolean blah=false;

    //TODO to delete!
    protected FloatBuffer[] textCoordBuffer;

    public BglAnimatedSprite(float x, float y, float w, float h, SpriteSheet[] spritesheet) {

        super(x, y, w, h, spritesheet[0].getTexture_id());
        this.spriteSheet = spritesheet;
        /* to increase the size of texturehandle[] when more than one texture */
        textureHandle = new int[spritesheet.length];
        textCoordBuffer = TextureCoordCalculator.calculate(x,y,w,h, spritesheet);
        number_of_frame_real = new int[spritesheet.length];
        for (int i=0; i<spritesheet.length; i++){
            number_of_frame_real[i] = spritesheet[i].getNumber_of_frame_real();
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

    @Override
    public void update(float dt){
        //super();
        animate();
    }


    public int getTextureState(){
        return state;
    }

    public FloatBuffer textCoordBufferGet() {
        return textCoordBuffer[inner_state];
    }

    public int  textureHandleGet() {
        return textureHandle[inner_state];
    }

    public void setTextureHandle() {
        final BtextureManager textManager = BtextureManager.getInstance();

        for (int i=0; i < spriteSheet.length; i++){
            textureHandle[i] = textManager.findHandle( spriteSheet[i].getTexture_id() );
        }
    }
}
