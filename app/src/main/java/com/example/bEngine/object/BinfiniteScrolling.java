package com.example.bEngine.object;

import com.google.common.base.Preconditions;

import java.util.PropertyResourceBundle;

/**
 * Created by Ben on 2/17/14.
 */
public class BinfiniteScrolling extends Bobject {

    private BglObject sprite1;
    private BglObject sprite2;
    private BglObject sprite3;

    private BglObject leader;
    private BglObject leader2;
    private BglObject leader3;

    public BinfiniteScrolling( BglObject sprite1, BglObject sprite2, BglObject sprite3, int x, int y, int w, int h )
    {
        super(x, y, w, h );

        this.sprite1 = Preconditions.checkNotNull(sprite1, this);
        this.sprite2 = Preconditions.checkNotNull(sprite2, this);
        this.sprite3 = Preconditions.checkNotNull(sprite3, this);


       // sprite1.setDisregardCam(true);
       // sprite2.setDisregardCam(true);


        sprite1.setPos(x, y);
        sprite2.setPos(x, y - sprite1.getSizeH());
        sprite3.setPos(x, y - sprite2.getPosY() - sprite2.getSizeH());

        leader = sprite1;
        leader2 = sprite2;
        leader3 = sprite3;
    }

    @Override
    public void update(float dt){

        if ( sprite1.getPosY() >= 1 ){
            sprite1.setPos( leader3.getPosX(), leader3.getPosY() - leader3.getSizeH() );
            leader = sprite2;
            leader2 = sprite3;
            leader3 = sprite1;
        }

        if ( sprite2.getPosY() >= 1 ){
            sprite2.setPos( leader3.getPosX(), leader3.getPosY() - leader3.getSizeH() );
            leader = sprite3;
            leader2=sprite1;
            leader3=sprite2;
        }

        if ( sprite3.getPosY() >= 1 ){
            sprite3.setPos( leader3.getPosX(), leader3.getPosY() - leader3.getSizeH() );
            leader = sprite1;
            leader2 = sprite2;
            leader3 = sprite3;
        }


        leader.setPos( leader.getPosX(), leader.getPosY() + 0.008f );
        leader2.setPos( leader.getPosX(), leader.getPosY() - leader.getSizeH() );
        leader3.setPos( leader2.getPosX(), leader2.getPosY() - leader2.getSizeH() );

    }
}
