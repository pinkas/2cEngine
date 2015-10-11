package com.benpinkas.helloben.leCasseBrique.wizard;

import com.benpinkas.bEngine.Action;

public class WizAttack extends Action {

    private Wizard actor;
    private Projectile projectile;

    public WizAttack(Wizard actor, Projectile projectile){
        this.actor = actor;
        this.projectile = projectile;
        period = 150;
    }

    @Override
    public void init() {
        projectile.setPos(actor);
        // x distance between the wiz and the bat
        if(actor.getBat().getPosX() > actor.getPosX()){
            projectile.setPos(actor, actor.getSizeW()/2.5f, 0);
        }

        float dist = actor.getBat().getPosX() - actor.getPosX();
        projectile.shoot(dist*2, 0.3f);
    }

    @Override
    public void desInit() {
//        projectile.stop();
    }

    @Override
    public boolean update(float dt) {
        return false;
    }
}
