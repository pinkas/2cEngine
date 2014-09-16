package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.scene.Scene;

/**
 * Created by Ben on 13-Jul-14.
 */
public class SceneForBall extends Scene {

    private Ball myBall = new Ball(0.5f,0.8f, 0.045f, 0.025f, 0.1f, 0.7f, 1);
    private Brick[] destroyMe = new Brick[300];
    private Bat bat = new Bat();

    public SceneForBall(){
        super("myBalls");

        float paddX = 0.04f;
        float paddY = 0.01f;

        int row = 10;
        int col = 11;

        float sizeW = 0.075f;
        float sizeH = 0.03f;

        for (int i=0; i < row; i++)
        {
            for (int j=0; j < col; j++){
                //TODO overwriting a few references ...
                destroyMe[i] = new Brick();
                destroyMe[i].setSize(sizeW, sizeH);
                destroyMe[i].setPos(paddX + j*(sizeW+0.01f), paddY + i*(sizeH+0.01f) );
                destroyMe[i].setColor(j*0.05f, j*0.1f, 1.0f, 1.0f);
               // destroyMe[i].setZ(-2.0f);
                add(destroyMe[i]);
                destroyMe[i].initParticles();
                //add(destroyMe[i].getParticles(0));
                //add(destroyMe[i].getParticles(1));
                //add(destroyMe[i].getParticles(2));
                //add(destroyMe[i].getParticles(3));
            }

        }

        add(bat);

        add(myBall);
        myBall.fire(0.1f, -0.25f);

        //SceneManager.initAllArea();
    }

    public void update(float dt){

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
