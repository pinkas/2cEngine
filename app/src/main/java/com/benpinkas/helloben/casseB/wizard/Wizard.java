package com.benpinkas.helloben.casseB.wizard;

import com.benpinkas.bEngine.object.BglAnimatedSprite;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.helloben.casseB.Bat;


public class Wizard extends BglAnimatedSprite {
    private int progressStill;
    private int progressCast;

    public enum WizState { ON, OFF }
    private WizState state = WizState.OFF;

    private int progressMove;

    private Bat bat;
    private Projectile projectile;
    private float destX;
    int MOVE_T = 200;
    int CAST_T = 300;
    int STILL_T;

    ///////////////////////
    private Action[] actions;
    //////////////////////

    public Wizard(float x, float y, float w, float h, SpriteSheet[] spritesheet, Bat bat,
                  Projectile projectile) {
        super(x, y, w, h, spritesheet);
        this.bat = bat;
        STILL_T = spritesheet[3].getTotal_duration() + 3;
        this.projectile = projectile;
    }

    public void setActions(Action[] actions){
        this.actions = actions;
    }

    @Override
    public void update(float dt){
        super.update(dt);

         //TODO change that
        if (state == WizState.OFF){
            return;
        }

        for (Action a : actions){
            if (a.isInProgress()){
                if (a.update()){
                    a.stop();
                }
                //LOts of work to do here!
                return;
            }
        }

        progressMove++;
        if (progressMove%MOVE_T==0) {
            progressMove = 0;
            move();
        }

        progressCast++;
        if (progressCast%CAST_T==0) {
            progressCast = 0;
            cast();
        }
    }

    public void cast(){
        actions[1].start();
    }
    public void move(){
        actions[0].start();
    }
    public void attack(){
        actions[2].start();
    }

    public Projectile getProjectile(){
        return projectile;
    }

    public Bat getBat() {
        return bat;
    }

    public WizState getState() {
        return state;
    }

    public void setState(WizState state) {
        this.state = state;
    }
}
