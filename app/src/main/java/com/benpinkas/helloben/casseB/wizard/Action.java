package com.benpinkas.helloben.casseB.wizard;

public abstract class Action {

    protected boolean inProgress = false;

    public Action() {}

    public final void start(){
        inProgress = true;
        init();
    }

    public final void stop(){
        inProgress = false;
        desInit();
    }

    public void init() {};
    public void desInit() {};

    public abstract boolean update();

    public final boolean isInProgress() {
        return inProgress;
    }
}
