package com.example.helloben;


/**
 * Created by Ben on 12-Apr-14.
 */
public class AttackLaser  extends Attack{

    public AttackLaser( SpaceShip ship, float coord[], float padX, float padY, int timer, float sizeX, float sizeY, int duration ){
        super( ship, coord, padX, padY );
        projList.get(0).setSize(sizeX, sizeY);
        projList.get(0).setLifetime(10);
        projList.get(0).setHp(2);
    }


}

