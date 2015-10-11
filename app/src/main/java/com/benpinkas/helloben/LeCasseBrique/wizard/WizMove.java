package com.benpinkas.helloben.leCasseBrique.wizard;


import com.benpinkas.bEngine.Action;

public class WizMove extends Action {

    private static final int MOVE_T = 200;
    private Wizard actor;
    private float XMINI = 0.01f;
    private float destX = 0.5f;

    public WizMove(Wizard actor){
        this.actor = actor;
        period = MOVE_T;
    }

    @Override
    public void desInit() {
        // set SpriteSheet
    }

    @Override
    public void init() {
        // set SpriteSheet
        destX = actor.getBat().getPosX();
    }

    @Override
    public boolean update(float dt) {
        float currX = actor.getPosX();
        float destY = actor.getPosY();
        float dx = destX - currX;
        if (Math.abs(dx) > XMINI) {
            actor.setPos(currX + dx/6f, destY);
            return true;
        } else {
            return false;
        }
    }

}
