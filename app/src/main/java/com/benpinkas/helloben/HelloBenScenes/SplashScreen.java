package com.benpinkas.helloben.HelloBenScenes;

import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.helloben.Button;
import com.benpinkas.R;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 2/3/14.
 */
public class SplashScreen extends Scene {

   BglSprite theSplashSprite;

    public SplashScreen(){
        super("splash");
        theSplashSprite = new BglSprite( 0, 0, 1, 1, new int[] {R.drawable.bengine} );
        add(theSplashSprite);
//        theSplashSprite.anchorPointSet(0.0f, 0.0f);


        final Button button = new Button(0.5f, 0.5f, 0.1f, 0.1f, new int[] {R.drawable.start},
                new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        stop();
                        SceneManager.startScene("myBalls");
                        return null;
                    }
                });
        button.setPos(0.5f, 0.5f);
        add(button);
       // setMemberLayer( button, 1 );

    }


    @Override
    public void stop() {
     //   SceneManager.getInstance().
        SceneManager.startScene("gameScene");
        this.setVisible(false);
        // give the focus to another Scene!!
        // That's why i can't get the heroe to jump when taping it at the moment.
    }

    @Override
    public void start() {
        setVisible(true);
        SceneManager.setInputFocus( this );

 //       SceneManager.getInstance().setFocusScene("splash");

    }
}
