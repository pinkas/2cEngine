package com.example.helloben;

import android.graphics.RectF;

import com.example.bEngine.object.BglAnimatedSprite;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.object.SpriteSheet;
import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 3/13/14.
 */
public class SpaceShip extends BglAnimatedSprite {

    protected Scene scene;
    protected RectF rect;

    public SpaceShip( float x, float y, float w, float h, SpriteSheet[] sheet,  Scene scene ){
        super(x, y, w, h, sheet);
        this.scene = scene;
        rect = new RectF();
    }

    public void shoot ( float posInitX, float posInitY, float dirX, float dirY ){
        Projectile proj = new Projectile( posInitX, posInitY, dirX, dirY) ;
        scene.addAsync(proj);
    }

    public boolean isReachable (SpaceShip ship, float dirX, float dirY){

        float enemyWidth = ship.getSize().x;
        float enemyX = ship.getPos().x;

        if ( enemyX < this.pos.x  && enemyX+enemyWidth>this.pos.x ) {
            return true;
        }
        else{
            return false;
        }
    }


}
