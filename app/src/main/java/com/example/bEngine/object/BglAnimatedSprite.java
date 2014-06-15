package com.example.bEngine.object;


import com.example.bEngine.service.GlService;


/**
 * Created by Ben on 9/26/13.
 */
public class BglAnimatedSprite  extends BglSprite {

    private final int [] number_of_frame_real;

    private int state;
    private int inner_state = 0; //TODO rename inner_state

    private SpriteSheet[] spriteSheet;

    public BglAnimatedSprite(float x, float y, float w, float h, SpriteSheet[] spritesheet) {
        super(x, y, w, h );

        //TODO UGLY!!!!
        int[] theRes = new int[spritesheet.length];
        for (int i=0; i<spritesheet.length;i++)
            theRes[i]=spritesheet[i].getTexture_id();
        setRes(theRes);

        glService = new GlService("basic", false, 1.0f);
        /* to increase the size of texturehandle[] when more than one texture */
        glService.resizeTextureHandle(spritesheet.length);
        glService.recalculateTextCoord(x, y, w, h, spritesheet);
        number_of_frame_real = new int[spritesheet.length];
        for (int i=0; i<spritesheet.length; i++){
            number_of_frame_real[i] = spritesheet[i].getNumber_of_frame_real();
        }
        this.spriteSheet = spritesheet;

    }

    public void animate (){
        //TODO have a class that does this kind of counter for me. (eg incrementing a counter)
        state ++;
        if (state >= number_of_frame_real[inner_state] ){
                state = 0;
        }
        glService.setTextCoordIndex(state);
    }

    @Override
    public void update(float dt){
        animate();
    }


    public int getTextureState(){
        return state;
    }

//    public FloatBuffer textCoordBufferGet() {
//        return textCoordBuffer[inner_state];
//    }

//    public int  textureHandleGet() {
//        return textureHandle[inner_state];
//    }

    public void setTextureHandle() {

        for (int i=0; i < spriteSheet.length; i++){
  //          textureHandle[i] = textManager.findHandle( spriteSheet[i].getTexture_id() );
        }
    }
}
