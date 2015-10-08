package com.benpinkas.helloben.LeCasseBrique;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;

import java.util.concurrent.Callable;

/**
 * Created by ben on 5/18/2015.
 */
public class SplashScene extends Scene {


    private final BglSprite bengineLogo2 = new BglSprite(0, 0, 1, 1,
            new int[] {R.drawable.bengine});

    private int cpt;

    public SplashScene() {
        super("splashScene");
        add(bengineLogo2);
        bengineLogo2.setVisible(false);

    }

    @Override
    public void start() {
        bengineLogo2.setVisible(true);

        new Btimer(100, new Callable() {
            @Override
            public Boolean call() {
                stop();
                return false;
            }
        } );
    }

    @Override
    public void stop() {

//        final int fadeOutDelay = 260;
        final int fadeOutDelay = 50;
        new Btimer(1, new Callable() {
            @Override
            public Boolean call() {

                fadeOut();
                if (cpt > fadeOutDelay) {
                    System.out.println("END");
                    bengineLogo2.setVisible(false);
                    SceneManager.startScene("startScene");
                    return false;
                }
                return  true;
            }
        } );
    }

    public void fadeOut(){
        float opacity = bengineLogo2.glService.getAlpha();
//        opacity = opacity - 0.005f;

        opacity = opacity - 0.05f;
        bengineLogo2.glService.setAlpha(opacity);
        cpt++;
    }
}
