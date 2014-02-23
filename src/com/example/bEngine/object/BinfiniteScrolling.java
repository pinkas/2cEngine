package com.example.bEngine.object;


/**
 * Created by Ben on 2/17/14.
 */
public class BinfiniteScrolling extends Bobject {

    private BglObject sprite1;
    private BglObject sprite2;

    public BinfiniteScrolling( BglObject sprite1, BglObject sprite2, int x, int y, int w, int h ){
        super(x, y, w, h );
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;

        sprite1.setDisregardCam(true);
        sprite2.setDisregardCam(true);

        sprite1.setPos(x, y);
        sprite2.setPos(x, y - sprite1.sizeGet().y );

    }

    @Override
    public void update(){

        if ( sprite1.getPos().y > 1 ){
            sprite1.setPos( sprite1.getPos().x , - sprite1.sizeGet().y );
        }
        sprite1.setPos( sprite1.getPos().x, sprite1.getPos().y + 0.005f );

        if ( sprite2.getPos().y > 1 ){
            sprite2.setPos( sprite2.getPos().x , - sprite2.sizeGet().y );
        }
        sprite2.setPos( sprite2.getPos().x, sprite2.getPos().y + 0.005f );

    }


}
