package com.benpinkas.helloben;


import com.benpinkas.bEngine.object.BglAnimatedSprite;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.bEngine.scene.Scene;

/**
 * Created by Ben on 3/13/14.
 */
public class SpaceShip extends BglAnimatedSprite {

    protected Scene scene;

    public SpaceShip( float x, float y, float w, float h, SpriteSheet[] sheet,  Scene scene ){
        super(x, y, w, h, sheet);
//        super( x, y, w, h,0.1f, 0.01f, 0.5f, 0.7f );
        this.scene = scene;
    }



    public boolean isReachable (SpaceShip ship, float dirX, float dirY){

        float enemyWidth = ship.getSizeW();
        float enemyX = ship.getPosX();

        /*
        if ( enemyX < this.pos.x  && enemyX+enemyWidth>this.pos.x ) {
            return true;
        }

        else{
            return false;
        }
        */
        return false;
    }


}
