package com.example.helloben.HelloBenScenes;

import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.scene.Scene;
import com.example.bEngine.scene.SceneManager;
import com.example.helloben.SpaceShip;
import com.example.helloben.SpaceShipEnemy;
import com.example.helloben.SpaceShipPlayer;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 3/13/14.
 */
public class GameScene extends Scene {

    public GameScene(){

        super("gameScene");
        final SpaceShipPlayer ship = new SpaceShipPlayer(this);
        add(ship);
        final SpaceShipEnemy shipEnemy = new SpaceShipEnemy(this);
        add(shipEnemy);

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
