package com.benpinkas.helloben.leCasseBrique;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.helloben.Button;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 17-Sep-14.
 */
public class StartScene extends Scene {

    private final BglSprite title;
    private final Button startButton;

    private final static int DUST_EXPLO_X = 100;
    private final static int DUST_EXPLO_Y = 1;

    public StartScene() {
        super("startScene");

        // Bg
        BglSprite bg = new BglSprite( 0,0,1,1, new int[] {R.drawable.bg});
        add(bg);

        // Title sprite "Dust"
        title = new BglSprite(0.25f,0.1f, 0.65f, 0.4f, new int[] {R.drawable.dust});
        title.setVisible(false);
        add(title);

        // Start Button
        startButton = new Button(0.7f, 0.88f, 0.3f, 0.25f, new int[] {R.drawable.start},
                new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                stop();
                SceneManager.startScene("myBalls");
                return null;
            }
        });
        startButton.setPos(0.5f, 0.65f, 1, 1);
        startButton.setVisible(false);
        add(startButton);
    }

    @Override
    public void start() {
        SceneManager.setInputFocus(this);
        title.setVisible(true);
        startButton.setVisible(true);
    }

    @Override
    public void stop() {
        title.setVisible(false);
        startButton.setVisible(false);
    }
}
