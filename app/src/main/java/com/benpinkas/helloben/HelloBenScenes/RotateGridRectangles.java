package com.benpinkas.helloben.HelloBenScenes;

import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.Joypad;
import com.benpinkas.bEngine.scene.Scene;
import com.benpinkas.helloben.RectangleRotateGrid;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 12-Jul-14.
 */
public class RotateGridRectangles extends Scene {

    public RotateGridRectangles() {
        super("rotateGridRect");
        //Grid of rectangles that rotate
        RectangleRotateGrid myGrid = new RectangleRotateGrid(0, 0, 10, 14);
        for ( int i =0;i<10*14;i++)
            add(myGrid.getRects(i));

        final Joypad theJoypad = new Joypad();
        add(theJoypad);

        Callable joypadActionMove = new Callable() {
            @Override
            public Float call() {
                float x = Brenderer.getCamXworld() + InputStatus.touchDeltaX / (float) Brenderer.getScreenW();
                float y = Brenderer.getCamYworld() + InputStatus.touchDeltaY / (float) Brenderer.getScreenW();
                Brenderer.moveCam(x, y);
                return 0f;
            }
        };
        theJoypad.defineActionMove( joypadActionMove );
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

}
