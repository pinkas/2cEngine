package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.Bobject;

/**
 * Created by Ben on 07-Oct-14.
 */
public class ObjectPool {

    private Bobject[] obj;
    private boolean[] available;

    public ObjectPool(){}

    public ObjectPool( Bobject[] obj){
        this.obj = obj;
        available = new boolean[obj.length];
        for (boolean b : this.available){
            b = true;
        }
    }

    public void setPool( Bobject[] obj){
        this.obj = obj;
        available = new boolean[obj.length];
        for (boolean b : this.available){
            b = true;
        }
    }


    public Bobject getAvailableObj(){
        for(int i=0; i<obj.length; i++) {
            if( available[i] ){
                available[i] = false;
                return obj[i];
            }
        }
        return obj[0];
    }



    public boolean release(Bobject o){
        for(int i=0; i<obj.length; i++){
            if( obj[i].equals(o) ){
                available[i] = false;
                return true;
            }
        }
        return false;
    }

}
