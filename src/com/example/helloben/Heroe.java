package com.example.helloben;

import com.example.bEngine.object.BglAnimatedSprite;
import com.example.bEngine.object.SpriteSheet;

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
    public void touchDown() throws Exception {
        super.touchDown();
        jump();
        speed = 0;
    }

    @Override
    public void touchUp() {
        super.touchUp();
        speed = 0.002f;
    }

    @Override
    public void touchUpMove(float x, float y) {
        super.touchUpMove(x, y);
        touchUp();
    }

    @Override
    public void update() {

        super.update();
        pos.x = pos.x + speed;

        velY = velY + 0.008f;

        pos.y = pos.y + velY;
        alpha = alpha + velY;
        if (pos.y > 0.8f ){
            pos.y = 0.8f;
            alpha = 1.0f;
            velY = 0.0f;
        }
    }

    public void jump(){
        velY = -0.08f;
    }

}
