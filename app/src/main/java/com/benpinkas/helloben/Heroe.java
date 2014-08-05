package com.benpinkas.helloben;

import com.benpinkas.bEngine.object.BglAnimatedSprite;
import com.benpinkas.bEngine.object.SpriteSheet;

/**
 * Created by Ben on 11/6/13.
 */
public class Heroe extends BglAnimatedSprite {

    float speed = 0.0f;
    float velY = 0.0f;

    public Heroe(float x, float y, float w, float h, SpriteSheet[] spritesheet){
        super(x, y, w, h, spritesheet);
    }

    @Override
    public void touchDown() {
        super.touchDown();
        setAnimated(false);
        jump();
        speed = 0;
    }

    @Override
    public void touchUp() {
        super.touchUp();
        setAnimated(true);
        speed = 0.002f;
    }

    @Override
    public void touchUpMove(float x, float y) {
        super.touchUpMove(x, y);
        touchUp();
    }

    @Override
    public void update(float dt) {
/*
        super.update(dt);
        pos.x = pos.x + speed;

        velY = velY + 0.008f;

        pos.y = pos.y + velY;
        if (pos.y > 0.8f ){
            pos.y = 0.8f;
            velY = 0.0f;
        }
        */
    }

    public void jump(){
        velY = -0.08f;
    }

}