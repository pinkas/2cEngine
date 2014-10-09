package com.benpinkas.helloben.HelloBenScenes;

import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.Joypad;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.BinfiniteScrolling;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.bEngine.scene.SceneManager;
import com.benpinkas.R;
import com.benpinkas.helloben.SpaceShipEnemy;
import com.benpinkas.helloben.SpaceShipPlayer;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 3/13/14.
 */
public class GameScene extends Scene {

    public GameScene(){


        super("gameScene");

        /* Road scrolling */
        int[] roadResource = { R.drawable.road };
        final BglSprite road1 = new BglSprite(0 ,0, 1, 1, roadResource);
        final BglSprite road2 = new BglSprite(0 ,0, 1, 1, roadResource);
        final BglSprite road3 = new BglSprite(0 ,0, 1, 1, roadResource);
        //TODO this is bad design  to have to set manually if an object est concerne par les collisions?????!
        road1.setCollide(false);
        road2.setCollide(false);
        road3.setCollide(false);
        add(road1);
        add(road2);
        add(road3);
        final BinfiniteScrolling scrolling = new BinfiniteScrolling( road1, road2, road3, 0, 0, 1, 1 );
        add(scrolling);
        /*******************/

        SpriteSheet slug0 = new SpriteSheet(R.drawable.biker, 5, 5, 25);
        SpriteSheet blank = new SpriteSheet(R.drawable.blank, 1, 1, 1);

        final SpriteSheet [] spriteSheetTab = new SpriteSheet[1];
        spriteSheetTab[0] = slug0;

        final SpriteSheet [] spriteSheetTab2 = new SpriteSheet[1];
        spriteSheetTab2[0] = blank;

        final SpaceShipPlayer ship = new SpaceShipPlayer( spriteSheetTab, this);
        add(ship);

        final SpaceShipEnemy shipEnemy = new SpaceShipEnemy( spriteSheetTab2, this);
        add(shipEnemy);
        shipEnemy.setPos(0.5f, shipEnemy.getPosY());


        ship.setEnemy(shipEnemy);

        final Joypad theJoypad = new Joypad();
        add(theJoypad);

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {

            float x = ship.getPosX() + InputStatus.touchDeltaX;
            float y = ship.getPosY() + InputStatus.touchDeltaY;

            ship.setVel(InputStatus.touchDeltaX, InputStatus.touchDeltaY);
            ship.setPos(x, y);

            return 0f;
            }
        };


        Callable joypadActionUp = new Callable() {

            public Float call(){
                return 0f;
            }
        };

        theJoypad.defineActionMove( joypadActionMove );
        theJoypad.defineActionUp( joypadActionUp );
    }

    @Override
    public void start() {
        setVisible(true);
        SceneManager.setInputFocus( this );
    }

    @Override
    public void stop() {
        SceneManager.startScene("gameScene");
        this.setVisible(false);
        // give the focus to another Scene!!
        // That's why i can't get the heroe to jump when taping it at the moment.
    }


}
