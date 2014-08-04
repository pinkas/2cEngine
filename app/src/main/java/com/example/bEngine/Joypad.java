package com.example.bEngine;

import android.graphics.Point;

import com.example.bEngine.object.Brectangle;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 11/5/13.
 */
public class Joypad extends Brectangle  {

    Callable actionDown;
    Callable actionMove;
    Callable actionUp;

    public Joypad() {
        super( 0, 0, 100, 100, 0, 0, 0, 0 );

        setCollide(false);
    }



    @Override
    public void touchDown() {
        super.touchDown();
        try {
            actionDown.call();
        } catch (Exception e) {
            System.out.println("Joypad, no touchDown implemented!");
        }
    }

    @Override
    public void touchMove(float x, float y)  {
        super.touchMove(x, y);
        try {
            actionMove.call();
        } catch (Exception e) {
            System.out.println("Joypad, no touchMove implemented!");
        }
    }

    @Override
    public void touchUp()  {
        super.touchUp();
        try {
            actionUp.call();
        } catch (Exception e) {
            System.out.println("Joypad, no touchUp implemented!");
        }
    }

    public void defineActionDown ( Callable<Float> func ){
        actionDown = func;
    }

    public void defineActionMove (Callable<Float> func){
        actionMove = func;
    }

    public void defineActionUp (Callable<Float> func){
        actionUp = func;
    }
    public void rightAction(){

    }



}
