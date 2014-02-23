package com.example.helloben.HelloBenScenes;

import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.bEngine.object.BglSprite;
import com.example.bEngine.object.Brectangle;
import com.example.helloben.Button;
import com.example.helloben.R;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 2/3/14.
 */
public class SplashScreen extends Scene {

   BglSprite theSplashSprite;

    public SplashScreen(){
        super("splash");
        theSplashSprite = new BglSprite( 0, 0, 1, 1, R.drawable.splash );
        add(theSplashSprite);
        theSplashSprite.anchorPointSet(0.0f, 0.0f);

        final Brectangle button = new Button(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                SceneManager.getInstance().stopScene("splash");
                return null;
            }
        });
        button.setPos(0.5f, 0.5f);
        add(button);
        setMemberLayer( button, 1 );

    }


    @Override
    public void stop() {
     //   SceneManager.getInstance().
        SceneManager.getInstance().startScene("test");
        this.setVisible(false);
        // give the focus to another Scene!!
        // That's why i can't get the heroe to jump when taping it at the moment.
    }

    @Override
    public void start() {
        super.start();
        setVisible(true);
        SceneManager.getInstance().setFocusScene( this );

 //       SceneManager.getInstance().setFocusScene("splash");

    }
}
