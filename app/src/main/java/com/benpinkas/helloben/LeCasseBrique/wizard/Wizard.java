package com.benpinkas.helloben.LeCasseBrique.wizard;

import com.benpinkas.bEngine.Action;
import com.benpinkas.bEngine.object.BglAnimatedSprite;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.helloben.LeCasseBrique.Bat;


public class Wizard extends BglAnimatedSprite {
    private int progressStill;
    private int progressCast;

    public enum WizState { ON, OFF }
    private WizState state = WizState.OFF;

    private int progressMove;

    private Bat bat;
    private Projectile projectile;
    private float destX;
    int CAST_T = 300;
    int STILL_T;

    ///////////////////////
    private Action[] actions;
    //////////////////////

    public Wizard(float x, float y, float w, float h, SpriteSheet[] spritesheet, Bat bat,
                  Projectile projectile) {
        super(x, y, w, h, spritesheet);
        setSize(w*1.3f);
        this.bat = bat;
        STILL_T = spritesheet[0].getTotal_duration() + 3;
        this.projectile = projectile;
        name = "wizard";
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

        if(getBat().getPosX() < getPosX()){
            faceLeft();
        } else {
            faceRight();
        }

        actions[0].incCpt();
        actions[1].incCpt();
    }

    public boolean faceRight(){
        float dy = (180 - angle.y) / 10f;
        if (Math.abs(dy) > 0.0001){
            setAngleY(angle.y + dy);
            return true;
        } else {
            angle.y = 180;
            return false;
        }
    }

    public boolean faceLeft(){
         float dy = (0 - angle.y) / 10f;
        if (Math.abs(dy) > 0.0001){
            setAngleY(angle.y + dy);
            return true;
        } else {
            angle.y = 0;
            return false;
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
