package com.benpinkas.helloben.casseB;

import com.benpinkas.R;
import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.object.BglSprite;
import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.object.Brectangle;
import com.benpinkas.bEngine.service.CollisionService;
import com.benpinkas.bEngine.service.MessageManager;

/**
 * Created by Ben on 14-Jul-14.
 */
public class Bat extends BglSprite {

    private boolean touched;
    private final static float NORMAL_SIZE_W = 0.25f;
    private final static float NORMAL_SIZE_H = 0.06f;
    private final static float ENLARGE_FACTOR = 2f;

    public Bat(){
        super(0.4f, 0.9f, 0.25f, 0.06f, new int[] {R.drawable.bat});
        //anchorPointSet(0.5f,0.5f);
    }

    @Override
    public void touchMove(float x, float y) {
        setPos( x - 0.5f*getSizeW(), getPosY() );
    }
    @Override
    public void touchDown(){
        super.touchDown();
        touched = true;
    }

    public void touchUp(){
        super.touchUp();
        touched = false;
    }

    public void touchUpMove(float x, float y){
        super.touchUpMove(x,y);
        touched = false;
    }

    public void setBig(boolean big){
        if (big){
            setSize( NORMAL_SIZE_W * ENLARGE_FACTOR, NORMAL_SIZE_H );
        }
        else{
            setSize( NORMAL_SIZE_W, NORMAL_SIZE_H);
        }

    }

    public void update(float dt){
        if (touched) {
            setPos(InputStatus.touchXscr/(float) Brenderer.getScreenW() - 0.5f*getSizeW(), getPosY());
        }
    }


}
