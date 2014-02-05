package com.example.helloben.HelloBenScenes;

import com.example.bEngine.Scene;
import com.example.bEngine.SceneManager;
import com.example.bEngine.object.BglSprite;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.object.SpriteSheet;
import com.example.helloben.Button;
import com.example.helloben.Heroe;
import com.example.helloben.R;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 2/3/14.
 */
public class TestScene extends Scene {

    public TestScene(){
        super( "test" );
        final BglSprite clouds = new BglSprite( 0, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        add(clouds);
        clouds.anchorPointSet(0.0f,0.0f);
        clouds.zSet(0.7f);

        final BglSprite clouds2 = new BglSprite( 1.3f, 0, 1.3f, 0.5f, R.drawable.lastlayer );
        add(clouds2);
        clouds2.anchorPointSet(0.0f,0.0f);
        clouds2.zSet(0.7f);

        final BglSprite dune = new BglSprite( 0, 0.75f, 2.5f,0.44f, R.drawable.dune );
        add(dune);
        dune.anchorPointSet(0,1);
        dune.zSet(0.4f);

        final BglSprite bg1 = new BglSprite( 0, 1, 2.5f, 1f, R.drawable.firstlayer );
        add(bg1);
        bg1.anchorPointSet(0,1);


        SpriteSheet slug0 = new SpriteSheet(R.drawable.sprite, 12, 1, 12);
        SpriteSheet slug = new SpriteSheet(R.drawable.sprite2, 16, 1, 16);
        final SpriteSheet [] spriteSheetTab = new SpriteSheet[2];
        spriteSheetTab[0] = slug0;
        spriteSheetTab[1] = slug;

        final Heroe metal = new Heroe(0.0f, 0.8f, 0.135f, 0.2f, spriteSheetTab);
        add(metal);
        metal.anchorPointSet(0.0f,1.0f);
        metal.setAngleY(180);

        final Brectangle button = new Button( new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                SceneManager.getInstance().stopScene("test");
                return null;
            }
        });
        add(button);
    }

    @Override
    public void start() {
        super.start();
        this.setVisible(true);
        SceneManager.getInstance().setFocusScene(this);
    }

    @Override
    public void stop() {
        super.stop();
        this.setVisible(false);
        SceneManager.getInstance().startScene("splash");
    }
}
