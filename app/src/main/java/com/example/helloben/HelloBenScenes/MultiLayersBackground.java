package com.example.helloben.HelloBenScenes;

import com.example.bEngine.Brenderer;
import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.scene.Scene;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 10-Jul-14.
 */
public class MultiLayersBackground extends Scene {

    public MultiLayersBackground() {

        super("metalScene");

        //To test the dirty hack for multi layers bg
        float Z2 = 0.9f;
        float pos = 0.5f;
        Brectangle rect3 = new Brectangle( pos, pos, 0.1f, 0.3f, 1.0f, 1.0f, 1.0f );
        add(rect3);
        rect3.anchorPointSet(0.0f, 0.0f);
        Brectangle rect4 = new Brectangle( pos, pos, 0.1f, 0.3f, 0.8f, 0.4f, 0.2f );
        add(rect4);
        rect4.anchorPointSet(0.0f, 0.0f);
        rect4.zSet(Z2);

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
