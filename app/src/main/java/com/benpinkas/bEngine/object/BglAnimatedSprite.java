package com.benpinkas.bEngine.object;


import com.benpinkas.bEngine.service.GlService;

import java.util.concurrent.Callable;


/**
 * Created by Ben on 9/26/13.
 */
public class BglAnimatedSprite  extends BglSprite {

    // TODO rename this member
    private final int [] frameNumber;

    private int tick;
    private int animationIndex;
    private int textureIndex;
    private SpriteSheet[] spriteSheet;

    private Btimer animationTimer;

    public BglAnimatedSprite(float x, float y, float w, float h, final SpriteSheet[] spriteSheet) {
        super(x, y, w, h );

        this.spriteSheet = spriteSheet;

        //TODO UGLY!!!!
        int[] theRes = new int[spriteSheet.length];
        for (int i=0; i<spriteSheet.length;i++)
            theRes[i]=spriteSheet[i].getTexture_id();
        setRes(theRes);

        glService = new GlService(res, spriteSheet);
        frameNumber = new int[spriteSheet.length];
        for (int i=0; i<spriteSheet.length; i++){
            // - 1 because framnumber is then compared to an index that has a 0 to n-1 range ...
            frameNumber[i] = spriteSheet[i].getNumber_of_frame_real() - 1;
        }

    }

    public int getCurrentAnimationTotalDuration(){
        return spriteSheet[textureIndex].getTotal_duration();
    }

    public void setAnimationSpeed(int animationSpeed){
        animationTimer.setTickDest(animationSpeed);
    }

    public void setAnimated(boolean animated){
        animationTimer.setRunning(animated);
    }

    public void setTextureIndex(int i){
        textureIndex = i;
        animationIndex = 0;
        tick = 0;
        glService.setTextureHandleIndex(i);
        glService.setTextCoordPos(animationIndex);
    }

    @Override
    public int getTextureWidth() {
        return super.getTextureWidth() / spriteSheet[0].getNumber_of_frame_x();
    }

    @Override
    public int getTextureHeight() {
        return super.getTextureHeight() / spriteSheet[0].getNumber_of_frame_y();
    }

    @Override
    public void update(float dt){
        super.update(dt);
        tick++;
        if ( tick >= spriteSheet[textureIndex].get_current_duration(animationIndex) ) {

            tick = 0;
            animationIndex++;
            if (animationIndex > frameNumber[textureIndex]) {
                animationIndex = 0;
            }

            glService.setTextCoordPos(animationIndex);
            this.setDirty(true);
        }
    }





}
