package com.benpinkas.helloben.LeCasseBrique.wizard;

import com.benpinkas.bEngine.Action;

public class WizCast extends Action {

    private Wizard actor;
    private int castDuration = 10;
    private int CAST_T = 300;
    private int castProgress = 0;

    public WizCast(Wizard actor){
        this.actor = actor;
        period = CAST_T;
    }

    @Override
    public void init(){
        actor.setTextureIndex(1);
        castDuration = actor.getCurrentAnimationTotalDuration();
     //   castDuration = 100;
    }

    @Override
    public void desInit(){
        actor.attack();
        actor.setTextureIndex(0);
    }

    @Override
    public boolean update(float dt) {
        if(castProgress < castDuration) {
            castProgress ++;
            return true;
        } else {
            castProgress = 0;
            return false;
        }
    }

}
