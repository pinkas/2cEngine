package com.benpinkas.helloben.leCasseBrique.wizard;

import com.benpinkas.bEngine.Action;
import com.benpinkas.bEngine.object.BglAnimatedSprite;
import com.benpinkas.bEngine.object.SpriteSheet;
import com.benpinkas.helloben.leCasseBrique.Bat;


public class Wizard extends BglAnimatedSprite {
    private int progressStill;
    private int progressCast;
    private int offsetCpt;
    private float referenceHeight = 0;

    public enum WizState { ON, OFF }
    private WizState state = WizState.OFF;

    private int progressMove;

    private Bat bat;
    private Projectile projectile;
    private float destX;
    int CAST_T = 300;
    int STILL_T;

    private float[] offsetY = new float[300];
    ///////////////////////
    private Action[] actions;
    //////////////////////

    public Wizard(float x, float y, float w, float h, SpriteSheet[] spritesheet, Bat bat,
                  Projectile projectile) {
        super(x, y, w, h, spritesheet);
        this.bat = bat;
        STILL_T = spritesheet[0].getTotal_duration() + 3;
        this.projectile = projectile;
        name = "wizard";

        float t;
        for (int i=0; i<offsetY.length; i++) {
            // We want to go from 0 to 2PI in offsetY.length points
            // sinus etant 2PI periodique
            t = (i/(float)offsetY.length) * (float) Math.PI * 2;
            offsetY[i] = (float) Math.sin(t)*0.007f;
            System.out.println(offsetY[i]);
        }

    }

    public void setActions(Action[] actions){
        this.actions = actions;
    }

    @Override
    public void update(float dt){
        super.update(dt);

        setPos(getPosX(), referenceHeight + getCurrentOffset());

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

    private float getCurrentOffset(){
        offsetCpt++;
        if (offsetCpt >= offsetY.length){
            offsetCpt = 0;
        }
    //    System.out.println(offsetY[offsetCpt]);
        return offsetY[offsetCpt];
    }

    public void setReferenceHeight(float refHeight) {
        referenceHeight = refHeight;
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
