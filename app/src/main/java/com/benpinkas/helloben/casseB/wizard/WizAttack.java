package com.benpinkas.helloben.casseB.wizard;

public class WizAttack extends Action {

    private Wizard actor;
    private Projectile projectile;

    public WizAttack(Wizard actor, Projectile projectile){
        this.actor = actor;
        this.projectile = projectile;
    }

    @Override
    public void init() {
        projectile.setPos(actor);
        projectile.shoot(0, 0.3f);
    }

    @Override
    public void desInit() {
//        projectile.stop();
    }

    @Override
    public boolean update() {
        return true;
    }
}
