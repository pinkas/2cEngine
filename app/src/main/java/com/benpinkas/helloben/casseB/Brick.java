package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.object.Btimer;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 14-Jul-14.
 */
public class Brick extends BglSprite {

    private boolean hasBonus;
    private int remainingHp;
    private int totalHp;

    private float[] damping;
    private int damp_index;
    private float savedPos;

    public Brick(int totalHp){
        super(0.1f, 0.1f, 0.1f,0.1f, new int[] {R.drawable.brick_mario} );
        this.totalHp = totalHp;
        remainingHp = totalHp;

        damping = new float[ ]{ -0.006f, -0.009f, -0.008f, -0.007f, -0.004f };
    }

    public Brick(float x, float y, float w, float h){
        super(x,y,w,h, new int[] {R.drawable.brick_mario} );
    }

    @Override
    public void collision(Bobject collider, CollisionService.collisionSide cs) {
        remainingHp --;
        damp();
        if (remainingHp < 1) {
            MessageManager.sendMessage("explosion", this);
            this.setCollide(false);
            explode();
        }
    }

    public void init (){
        setCollide(true);
        setVisible(true);
    }

    public void damp(){

        savedPos = getPosY();

        new Btimer( 1,  new Callable() {
            @Override
            public Boolean call() {

                setPos( getPosX(),  savedPos+damping[damp_index]);
                if (damp_index == damping.length - 1 ) {
                    damp_index = 0;
                    setPos(getPosX(), savedPos);
                    return false;
                }
                else {
                    damp_index++;
                    return true;
                }
            }
        } );

    }


    public void setRemainingHp(int remainingHp) {
        this.remainingHp = remainingHp;
    }

    public void explode(){
        setVisible(false);
//        showParticles();
    }

}
