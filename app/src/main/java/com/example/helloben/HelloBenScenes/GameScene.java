package com.example.helloben.HelloBenScenes;

import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.object.BglSprite;
import com.example.bEngine.object.BinfiniteScrolling;
import com.example.bEngine.object.SpriteSheet;
import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.helloben.R;
import com.example.helloben.SpaceShipEnemy;
import com.example.helloben.SpaceShipPlayer;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 3/13/14.
 */
public class GameScene extends Scene {

    public GameScene(){


        super("gameScene");

        /* Road scrolling */
        final BglSprite road1 = new BglSprite(0 ,0, 1, 1, R.drawable.road);
        final BglSprite road2 = new BglSprite(0 ,0, 1, 1, R.drawable.road);
        final BglSprite road3 = new BglSprite(0 ,0, 1, 1, R.drawable.road);


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

        ship.setEnemy(shipEnemy);

        final Joypad theJoypad = new Joypad();
        add(theJoypad);

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {
            //TODO get size of the screen rather than hardcoded dim!!!!
            float fingerVelX = (InputStatus.touch.x - theJoypad.prev.x) / 768f;
            float fingerVelY = (InputStatus.touch.y - theJoypad.prev.y) / 1200f;
            float x = ship.getPos().x + fingerVelX;
            float y = ship.getPos().y + fingerVelY;

            ship.setVel(fingerVelX, fingerVelY);
            //System.out.println(fingerVelX + "  " + fingerVelY  );
            ship.setPos(x, y);

            theJoypad.prev.x = InputStatus.touch.x;
            theJoypad.prev.y = InputStatus.touch.y;

            return 0f;
            }
        };


        Callable joypadActionDown = new Callable() {
            @Override
            public Float call() {
                theJoypad.prev.x = InputStatus.touch.x;
                theJoypad.prev.y = InputStatus.touch.y;
                System.out.println("KEY PRESSED!!");
                return 0f;
            }
        };

        Callable joypadActionUp = new Callable() {

            public Float call(){
                return 0f;
            }
        };

        theJoypad.defineActionMove( joypadActionMove );



        theJoypad.defineActionDown( joypadActionDown );
        theJoypad.defineActionUp( joypadActionUp );


    }

    @Override
    public void start() {
        setVisible(true);
        SceneManager.getInstance().setFocusScene( this );

        //       SceneManager.getInstance().setFocusScene("splash");

    }

    @Override
    public void stop() {
        //   SceneManager.getInstance().
        SceneManager.getInstance().startScene("gameScene");
        this.setVisible(false);
        // give the focus to another Scene!!
        // That's why i can't get the heroe to jump when taping it at the moment.
    }


}
