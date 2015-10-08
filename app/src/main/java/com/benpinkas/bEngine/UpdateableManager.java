package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.Updatable;

import java.util.ArrayList;

public class UpdateableManager {

    private static ArrayList<Updatable> toUpdate = new ArrayList<Updatable>();
    private static ArrayList<Updatable> stopToUpdate = new ArrayList<Updatable>();


    public static void addToUpdate( Updatable u ){
        toUpdate.add( u );
    }

    public static void update(float dt){
        for ( Updatable u : toUpdate ) {
            if( !u.update(dt) ) {
                stopToUpdate.add(u);
            }
        }

        for ( Updatable u : stopToUpdate ) {
            // endcallback calls the desInit of the action, since an action may call
            // another (which add it to 'toUpdate' we call the endCallback here so that it does
            // add something to toUpdate while going throught it
            u.endCallback();
            toUpdate.remove(u);
        }
        stopToUpdate.clear();
    }
}
