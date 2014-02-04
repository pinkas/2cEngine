package com.example.helloben;

import com.example.bEngine.object.Brectangle;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 2/4/14.
 */
public class Button extends Brectangle {

    private Callable<Void> cb;

    public Button( Callable<Void> cb ){
        super();
        this.cb = cb;
    }

    @Override
    public void touchDown() throws Exception {
        super.touchDown();
        this.cb.call();
    }
}
