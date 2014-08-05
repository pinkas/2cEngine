package com.benpinkas.helloben.casseB;

import com.benpinkas.bEngine.Brenderer;
import com.benpinkas.bEngine.InputStatus;
import com.benpinkas.bEngine.object.Brectangle;

/**
 * Created by Ben on 14-Jul-14.
 */
public class Bat extends Brectangle {

    private boolean touched;

    public Bat(){
        super(0.4f, 0.9f, 0.25f, 0.06f, 0.5f,0.5f, 0.5f);
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

    public void update(float dt){
        if (touched) {
            setPos(InputStatus.getTouchX()/(float) Brenderer.getScreenW() - 0.5f*getSizeW(),
                    getPosY());
        }
    }


}
