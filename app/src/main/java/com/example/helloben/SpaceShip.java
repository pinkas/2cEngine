package com.example.helloben;


import com.example.bEngine.object.BglAnimatedSprite;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.object.SpriteSheet;
import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 3/13/14.
 */
public class SpaceShip extends Brectangle {

    protected Scene scene;

    public SpaceShip( float x, float y, float w, float h, SpriteSheet[] sheet,  Scene scene ){
//        super(x, y, w, h, sheet);
        super( x, y, w, h,0.1f, 0.01f, 0.5f, 0.7f );
        this.scene = scene;
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
