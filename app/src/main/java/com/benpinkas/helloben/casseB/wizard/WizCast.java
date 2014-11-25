package com.benpinkas.helloben.casseB.wizard;

public class WizCast extends Action {

    private Wizard actor;
    private int castCpt;
    private int castDuration;

    public WizCast(Wizard actor){
        this.actor = actor;
        castDuration = 10;
    }

    @Override
    public void init(){
        actor.setTextureIndex(3);
        castDuration = actor.getCurrentAnimationTotalDuration();
    }

    @Override
    public void desInit(){
        actor.attack();
        actor.setTextureIndex(0);
    }

    @Override
    public boolean update(){
        if(castCpt > castDuration) {
            System.out.println(castDuration);
            castCpt = 0;
            return true;
        } else {
            castCpt++;
            return false;
        }
    }

}
