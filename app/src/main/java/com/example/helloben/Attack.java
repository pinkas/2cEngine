package com.example.helloben;

import android.graphics.PointF;
import android.widget.Space;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 10-Apr-14.
 */
public class Attack {

    private SpaceShip spaceShip;
    private float coord[];
    private List<Projectile> projList;
    private PointF startPos;
    private int cpt=0;
    private int timer;


    public Attack( SpaceShip ship, float coord[], float padX, float padY, int timer ){
        this.spaceShip = ship;
        this.timer = timer;

        projList = new ArrayList<Projectile>();

        startPos = new PointF(padX, padY);

        boolean blah = true;
        float valx=0;
        float valy=0;

        for (int i=0; i <coord.length ;i++){

            if (blah) {
                blah = false;
                valx = coord[i];
            }
            else{
                blah = true;
                valy = coord[i];
                projList.add(new Projectile( valx, valy) )  ;
            }

        }
    }


    public void go() {

        cpt++;

        if (cpt > timer ) {
            cpt = 0;

            float posx = spaceShip.getPos().x + startPos.x*spaceShip.getSize().x;
            float posy = spaceShip.getPos().y + startPos.y*spaceShip.getSize().y;

            for ( Projectile proj : projList ){
                proj.setPos( posx, posy );
                proj.shoot();
            }
        }


    }

    public List<Projectile> getProjList() {
        return projList;
    }
}
