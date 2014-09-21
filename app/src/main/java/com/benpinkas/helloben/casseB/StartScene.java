package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Brectangle;
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

    public StartScene() {
        super("startScene");
        title = new BglSprite(0.25f,0.1f, 0.5f, 0.5f, new int[] {R.drawable.dust} );
        title.setVisible(false);
        add(title);

        button = new Button(0.7f, 0.88f, 0.3f, 0.25f, new int[] {R.drawable.start},
                new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                stop();
                SceneManager.startScene("myBalls");
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
