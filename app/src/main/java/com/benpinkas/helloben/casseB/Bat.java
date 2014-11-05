package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.Bcall;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

/**
 * Created by Ben on 14-Jul-14.
 */
public class Bat extends BglSprite {

    private boolean touched;
    private final static float NORMAL_SIZE_W = 0.25f;
    private final static float NORMAL_SIZE_H = 0.03f;
    private final static float ENLARGE_FACTOR = 2f;

    public Bat(){
        super(0.4f, 0.9f, NORMAL_SIZE_W, NORMAL_SIZE_H, new int[] {R.drawable.bat});

        MessageManager.addListener("bat_down", new Bcall<Void>() {
            public Void call(Object o) {

                return null;
            }
        }) ;

        MessageManager.addListener("bat_up", new Bcall<Void>() {
            public Void call(Object o) {
                return null;
            }
        }) ;

    }

    public void setBig(boolean big){
        if (big){
            setSize( NORMAL_SIZE_W * ENLARGE_FACTOR, NORMAL_SIZE_H );
        }
        else{
            setSize( NORMAL_SIZE_W, NORMAL_SIZE_H);
        }

    }

    public void moveBatToFinger(){
        setPos(InputStatus.touchXscr/(float) Brenderer.getScreenW() - 0.5f*getSizeW(), getPosY());
    }

    public void update(float dt){
    }


}
