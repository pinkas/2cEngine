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
        for (int i=0; i<available.length; i++){
            available[i] = true;
        }
    }

    public void setPool( Bobject[] obj){
        this.obj = obj;
        available = new boolean[obj.length];
        for (int i=0; i<available.length; i++){
            available[i] = true;
        }
    }


    public Bobject getAvailableObj(){
        for(int i=0; i<obj.length; i++) {
            if( available[i] ){
                available[i] = false;
                System.out.println("Object " + i + " " + " was allocated");
                return obj[i];
            }
        }
        System.out.println( " FAILED TO allocate !");

        return obj[0];
    }



    public boolean release(Bobject o){
        for(int i=0; i<obj.length; i++){
            if( obj[i] == o ){
                available[i] = true;
                System.out.println("Object " + i + " " + " was RELEASED!");
                return true;
            }
        }
        System.out.println( " FAILED TO RELEASE !");

        return false;
    }

}
