package com.benpinkas.helloben.HelloBenScenes;

import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.helloben.Heroe;
import com.benpinkas.R;

/**
 * Created by Ben on 2/3/14.
 */
public class TestScene extends Scene {

    public TestScene(){
        super( "test" );

        int[] res = {R.drawable.lastlayer};
        final BglSprite clouds = new BglSprite( 0, 0, 1.3f, 0.5f, res );
        add(clouds);
        clouds.setZ(0.7f);

        final BglSprite clouds2 = new BglSprite( 1.3f, 0, 1.3f, 0.5f, res );
        add(clouds2);
        clouds2.setZ(0.7f);

        int[] res2 = {R.drawable.dune};
        final BglSprite dune = new BglSprite( 0, 0.75f-0.3f, 2.5f,0.44f, res2 );
         add(dune);
        dune.setZ(0.4f);

        int[] res3 = {R.drawable.firstlayer};
        final BglSprite bg1 = new BglSprite( 0, 1-0.3f, 2.5f, 1f, res3 );
        add(bg1);

        SpriteSheet slug0 = new SpriteSheet(R.drawable.sprite, 12, 1, 12);
        SpriteSheet slug = new SpriteSheet(R.drawable.sprite2, 16, 1, 16);
        final SpriteSheet [] spriteSheetTab = new SpriteSheet[2];
        spriteSheetTab[0] = slug0;
        spriteSheetTab[1] = slug;

        final Heroe metal = new Heroe(0.0f, 0.8f, 0.135f, 0.2f, spriteSheetTab);
        add(metal);
//        metal.anchorPointSet(0.0f,1.0f);
        metal.setAngleY(180);

        /* Joypad yo */
/*
        final Joypad theJoypad = new Joypad();
        add(theJoypad);

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {
                float x = Brenderer.getCamXworld() + InputStatus.getTouchDelta().x / (float) Brenderer.getScreenW();
                float y = Brenderer.getCamYworld() + InputStatus.getTouchDelta().y / (float) Brenderer.getScreenW();
                Brenderer.moveCam(x, y);
                return 0f;
            }
        };
        theJoypad.defineActionMove( joypadActionMove );
*/
    }

    @Override
    public void start() {
        this.setVisible(true);
        SceneManager.setInputFocus(this);
    }

    @Override
    public void stop() {
        this.setVisible(false);
        SceneManager.startScene("splash");
    }
}