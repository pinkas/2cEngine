package com.example.helloben;

import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.bEngine.object.Brectangle;
import com.example.bEngine.scene.Scene;
import com.example.helloben.HelloBenScenes.GameScene;

import java.util.List;

/**
 * Created by Ben on 3/13/14.
 */
public class SpaceShip extends Brectangle {

    private Scene scene;


    public SpaceShip( float x, float y, float w, float h, float r, float g, float b,  Scene scene ){
        super(x, y, w, h, r, g, b);
        this.scene = scene;
    }

    public void shoot ( float posInitX, float posInitY, float dirX, float dirY ){
        Projectile proj = new Projectile( posInitX, posInitY, dirX, dirY) ;
        scene.addAsync(proj);
    }


}
