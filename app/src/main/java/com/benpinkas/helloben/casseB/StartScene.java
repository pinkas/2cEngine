package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.MatrixHelper;
import com.benpinkas.bEngine.effect.Explosion;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.helloben.Button;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 17-Sep-14.
 */
public class StartScene extends Scene {

    private final BglSprite title;
    private final Button button;

    private final static int DUST_EXPLO_X = 100;
    private final static int DUST_EXPLO_Y = 1;

    public StartScene() {
        super("startScene");

        // Bg
        BglSprite bg = new BglSprite( 0,0,1,1, new int[] {R.drawable.bg_cahier});
        add(bg);

        // Title sprite "Dust"
        title = new BglSprite(0.25f,0.1f, 0.5f, 0.5f, new int[] {R.drawable.dust});
        title.setVisible(false);
        add(title);

        // Explosion of the title
        Explosion explosion = new Explosion(title, DUST_EXPLO_X, DUST_EXPLO_Y);
        final BglSprite[] particles = explosion.getParticle();
        for (int i=0; i<particles.length; i++){
            add(particles[i]);
        }

        // Start Button
        button = new Button(0.7f, 0.88f, 0.3f, 0.25f, new int[] {R.drawable.start},
                new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                stop();

                new Btimer(1, new Callable() {
                    @Override
                    public Boolean call() {
                        float diff=0;
                        for (BglSprite p :particles){

                            diff = (0.5f-p.getPosX())/1000f;
                            p.setPos( p.getPosX() - diff, p.getPosY());
                        }
                        if (particles[51].getPosX()>1.1f){
                            for (BglSprite p :particles) {
                                p.setVisible(false);
                            }

                            SceneManager.startScene("myBalls");
                            return  false;
                        }
                        return true;
                    }
                } );


                //SceneManager.startScene("myBalls");
                return null;
            }
        });
        button.setPos(0.5f, 0.65f, 1, 1);
        button.setVisible(false);
        add(button);


    }

    @Override
    public void start() {
        SceneManager.setInputFocus(this);
        title.setVisible(true);
        button.setVisible(true);
    }

    @Override
    public void stop() {
        title.setVisible(false);
        button.setVisible(false);
    }
}
