package com.example.helloben.HelloBenScenes;

import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.helloben.SpaceShip;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 3/13/14.
 */
public class GameScene extends Scene {

    public GameScene(){

        super("gameScene");
        final SpaceShip ship = new SpaceShip();
        add(ship);

        final Joypad theJoypad = new Joypad();
        add(theJoypad);

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {

                float x = ship.getPos().x + ( InputStatus.touch.x - theJoypad.prev.x) / 1280f;
                float y = ship.getPos().y + ( InputStatus.touch.y - theJoypad.prev.y) / 720f;
                ship.setPos( x, y );
                theJoypad.prev.x = InputStatus.touch.x;
                theJoypad.prev.y = InputStatus.touch.y;

                System.out.println(x);

                return 0f;
            }
        };


        Callable joypadActionDown = new Callable() {
            @Override
            public Float call() {
                theJoypad.prev.x = InputStatus.touch.x;
                theJoypad.prev.y = InputStatus.touch.y;
                return 0f;
            }
        };

        theJoypad.defineActionMove( joypadActionMove );
        theJoypad.defineActionDown( joypadActionDown );


    }

    @Override
    public void start() {
        super.start();
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
