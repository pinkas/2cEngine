package com.benpinkas.helloben;

/**
 * Created by Ben on 15-May-14.
 */
public interface Phase {

    public int isDone();

    public void applyNewPhase();

    public void move();

    public void update();

}
