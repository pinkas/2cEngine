package com.example.helloben;

import android.graphics.RectF;

import com.example.bEngine.object.Brectangle;
import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 3/13/14.
 */
public class SpaceShip extends Brectangle {

    protected Scene scene;
    protected RectF rect;

    public SpaceShip( float x, float y, float w, float h, float r, float g, float b,  Scene scene ){
        super(x, y, w, h, r, g, b);
        this.scene = scene;
        rect = new RectF();
    }

    public void shoot ( float posInitX, float posInitY, float dirX, float dirY ){
        Projectile proj = new Projectile( posInitX, posInitY, dirX, dirY) ;
        scene.addAsync(proj);
    }

    public boolean isReachable (SpaceShip ship, float dirX, float dirY){


        float blahX = Math.abs( pos.x - ship.getPos().x );
        float blahY = Math.abs( pos.y - ship.getPos().y );

        float blahblahX = Math.abs(blahX / dirX);
        float blahblahY = Math.abs(blahY / dirY);

        float finalPosX;
        float finalPosY;

        if (blahblahX > blahblahY){
            finalPosX = ship.getPos().x ;
            finalPosY = pos.y + blahblahX * dirY;
        }
        else{
            finalPosY = ship.getPos().y;
            finalPosX = pos.x + blahblahY * dirX;
        }

        rect.set(finalPosX, finalPosY, finalPosX+size.x, finalPosY+size.y);
        if ( rect.intersect(ship.rect) ) {
            return true;
        }
        else{
            return false;
        }
    }


}
