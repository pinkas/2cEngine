package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.Updatable;

public abstract class Action implements Updatable {

    protected boolean inProgress = false;

    public Action() {}

    protected int cpt; // when cpt reaches period the action starts
    protected int period; // Number of frame before the action starts

    public final void start(){
        cpt = 0;
        inProgress = true;
        init();
        UpdateableManager.addToUpdate(this);
    }

    // TODO renomer start et endCallback
    public void endCallback(){
        inProgress = false;
        desInit();
    }

    public final void stop(){
        inProgress = false;
        desInit();
        //TODO remove from toUpdate tout en evitant concurence issue.||
    }

    public int getCpt(){
        return  cpt;
    }

    public void incCpt() {
        cpt++;
        if( cpt%period == 0) {
            cpt = 0;
            start();
        }
    }



    public void init() {};
    public void desInit() {};

    public final boolean isInProgress() {
        return inProgress;
    }
}
