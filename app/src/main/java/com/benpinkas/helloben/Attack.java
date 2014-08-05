package com.benpinkas.helloben;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 10-Apr-14.
 */
public class Attack {

    protected SpaceShip spaceShip;
    protected List<Projectile> projList;
    protected PointF startPos;
    protected int cpt=0;

    public Attack( SpaceShip ship, float coord[], float padX, float padY ){
        this.spaceShip = ship;

        projList = new ArrayList<Projectile>();
        startPos = new PointF(padX, padY);

        boolean doXfield = true;
        float valx=0;
        float valy=0;

        for( float theCoord : coord ){
            if (doXfield) {
                doXfield = false;
                valx = theCoord;
            }
            else{
                doXfield = true;
                valy = theCoord;
                projList.add(new Projectile( valx, valy) )  ;
                //projList.  .setVisible(false);
            }
        }
    }

    //TODO could be array?
    public List<Projectile> getProjList() {
        return projList;
    }


    public void initProjectiles(){
        float posx = spaceShip.getPosX() + startPos.x*spaceShip.getSizeW();
        float posy = spaceShip.getPosY() + startPos.y*spaceShip.getSizeH();

        for ( Projectile proj : projList ){
            proj.setVisible(true);
            proj.setPos( posx, posy );
            proj.shoot();
        }
    }

}
