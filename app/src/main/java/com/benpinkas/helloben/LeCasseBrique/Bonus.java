package com.benpinkas.helloben.LeCasseBrique;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

public class Bonus extends Brectangle {

    public enum BonusType {BALL_SPEED, BAT_SIZE};
    private BonusType bonusType = BonusType.BALL_SPEED;
    private String bonusTypeString;

    public Bonus(float x, float y, float w, float h, BonusType type) {
        super(x, y, w, h, 1.0f, 1.0f, 1.0f);
        setBonusType(type);
    }

    public String getBonusTypeString() {
        return bonusTypeString;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        float posy = getPosY();
        if (posy > 1){
            MessageManager.sendMessage("bonus_out_of_screen", this);
            setVisible(false);
        } else {
            setPos(getPosX(), posy + getVel().y * dt);
            setAngleX(getAngleX()+5 );
            setAngleY(getAngleY()+2 );

        }
    }

    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        super.collision(collider, cs);
        if ( collider instanceof Bat ) {
            setVisible(false);
            MessageManager.sendMessage(getBonusTypeString(), this);
        }
    }

    public void setBonusType(BonusType bt){
        switch (bt) {
            case BALL_SPEED:
                setColor(1f,0.5f,0f,1f);
                bonusTypeString = "bonus_ball_speed";
                break;

            case BAT_SIZE:
                setColor(0f,1f,0f,1f);
                bonusTypeString = "bonus_bat_size";
                break;
        }
    }

    public static BonusType getRandomBonusType(){
        double rand = Math.random();
        if (rand < 0.5 ){
            return BonusType.BALL_SPEED;
        }
        else if (rand >= 0.5) {
            return BonusType.BAT_SIZE;
        }
        else return BonusType.BALL_SPEED;
    }

}
