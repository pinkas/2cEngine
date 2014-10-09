package com.benpinkas.helloben.casseB;

import android.os.Message;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

/**
 * Created by Ben on 06-Oct-14.
 */
public class Bonus extends Brectangle {

    private final static float VEL_Y = 0.25f;

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
            setPos(getPosX(), posy + VEL_Y * dt);
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
                setColor(1f,0f,0f,1f);
                bonusTypeString = "bonus_ball_speed";
                break;

            case BAT_SIZE:
                setColor(0f,1f,0f,1f);
                bonusTypeString = "bonus_bat_size";
                break;
        }
    }

}
