package com.example.helloben;

import com.example.bEngine.scene.Scene;

/**
 * Created by Ben on 10-Apr-14.
 */
public class SpaceShipEnemy extends SpaceShip {

    private float stepY = 0.01f;

    public SpaceShipEnemy( Scene scene ){
        super(0.8f, 0, 0.2f, 0.27f, 0.8f, 0.8f, 0.5f, scene);
    }

    @Override
    public void update() {
        super.update();

        setColor(0.8f,0.8f,0.5f,1.0f);

        pos.y = pos.y + stepY;

        if (pos.y + size.y > 1.0f  || pos.y < 0 ){
            stepY = - stepY;
        }

        double fire = Math.random();
        if ( fire > 0.97f ){


            shoot( pos.x -0.01f, pos.y + size.y/2, -0.01f, -0.005f);
            shoot( pos.x -0.01f, pos.y + size.y/2, -0.01f, 0);
            shoot( pos.x -0.01f, pos.y + size.y/2, -0.01f, 0.005f);

        }
    }

    @Override
    public void collision(){
        setColor(1.0f,1.00f,1.0f,0f);
    }

}
