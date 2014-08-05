package com.benpinkas.bEngine.object;


import com.benpinkas.bEngine.service.GlService;

import java.util.concurrent.Callable;


/**
 * Created by Ben on 9/26/13.
 */
public class BglAnimatedSprite  extends BglSprite {

    private final int [] frameNumber;

    private int animationSpeed = 3;
    private int animationIndex;
    private int textureIndex;

    private Btimer animationTimer;

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
        frameNumber = new int[spritesheet.length];
        for (int i=0; i<spritesheet.length; i++){
            frameNumber[i] = spritesheet[i].getNumber_of_frame_real();
        }

        animationTimer = new Btimer(animationSpeed, new Callable() {
            @Override
            public Boolean call() {
                animationIndex ++;
                if (animationIndex >= frameNumber[textureIndex] ){
                    animationIndex = 0;
                }
                glService.setTextCoordPos(animationIndex);
                dirty = true;
                return true;
            }
        } );

    }

    public void setAnimationSpeed(int animationSpeed){
        animationTimer.setTickDest(animationSpeed);
    }

    public void setAnimated(boolean animated){
        animationTimer.setRunning(animated);
    }

    public void setTextureIndex(int i){
        glService.setTextureHandleIndex(i);
    }

    @Override
    public void update(float dt){
    }





}
