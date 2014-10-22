package com.benpinkas.helloben.casseB;

import android.graphics.PointF;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.CollisionService.collisionSide;
import com.benpinkas.bEngine.service.MessageManager;


/**
 * Created by Ben on 13-Jul-14.
 */
public class Ball extends BglSprite {

    private PointF prevPos = new PointF();
    private PointF rawVel = new PointF();

    private boolean collisionDuringThisFrame;

    private static float BAT_FACTOR = 3.5f;
    private static final float BASE_VEL = 0.45f;
    private float speedFactor = 1;

    public Ball(float x, float y, float w, float h, float r, float g, float b){
        super(x,y,w,h, new int[] {R.drawable.ball} );
        vel.x = BASE_VEL;
        vel.y = BASE_VEL;
    }


    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        //pos = prevPos;
        if ( collider instanceof Bat && !collisionDuringThisFrame ) {
            collisionDuringThisFrame = true;

            float batCenterX = collider.getPosX(0.5f);
            float ballCenterX = this.getPosX(0.5f);

            vel.y = - vel.y;
            vel.x = (ballCenterX - batCenterX) * BAT_FACTOR;
        }
        else if ( collider instanceof Brick || collider instanceof Brectangle){
            if(cs == collisionSide.LEFT || cs == collisionSide.RIGHT ){
                vel.x = -vel.x;
            }
            if (cs == collisionSide.BOTTOM || cs == collisionSide.TOP ){
                vel.y = -vel.y;
            }
        }
    }

    @Override
    public void update(float dt) {
        collisionDuringThisFrame = false;

        if (getPosY() > 0.95f ) {
            MessageManager.sendMessage("lost_ball");
        }

        setAngleZ(getAngleZ() +2*speedFactor);

        float newPosX = this.getPosX() + vel.x * speedFactor * dt;
        float newPosY = this.getPosY() + vel.y * speedFactor * dt;
        setPos (newPosX, newPosY);
    }

    public void setSpeedFactor(int speedFactor){
        this.speedFactor = speedFactor;
    }

}
