package com.benpinkas.bEngine.object;

import com.benpinkas.bEngine.scene.SceneManager;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 13-Jul-14.
 */
public class Btimer implements Updatable {

    private int tick;
    private int tickDest;

    private boolean running = true;

    private Callable<Boolean> cb;


    public Btimer( int tickDest, Callable<Boolean> cb )
    {
        this.tickDest = tickDest;
        this.cb = cb;
        SceneManager.addTimer(this);
    }

    @Override
    public void update(float dt) {

        if (!running) return;

        tick++;
        if ( tick >= tickDest ){
            tick = 0;
            try {
                if(!cb.call())
                    running = false;
            }
            catch (Exception e) {System.out.println(e);}
        }
    }

    public void setTickDest(int tickDest){
        this.tickDest = tickDest;
        tick = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        tick = 0;
        this.running = running;
    }
}
