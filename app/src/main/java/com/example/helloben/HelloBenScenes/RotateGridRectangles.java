package com.example.helloben.HelloBenScenes;

import com.example.bEngine.Brenderer;
import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.scene.Scene;
import com.example.helloben.R;
import com.example.helloben.RectangleRotateGrid;

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
                float x = Brenderer.getCamXworld() + InputStatus.getTouchDelta().x / (float) Brenderer.getScreenW();
                float y = Brenderer.getCamYworld() + InputStatus.getTouchDelta().y / (float) Brenderer.getScreenW();
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
