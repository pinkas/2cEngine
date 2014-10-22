package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.Bobject;

/**
 * Created by Ben on 07-Oct-14.
 */
public class ObjectPool {

    private Object[] obj;
    private boolean[] available;

    public ObjectPool(){}

    public ObjectPool( Object[] obj){
        this.obj = obj;
        available = new boolean[obj.length];
        for (int i=0; i<available.length; i++){
            available[i] = true;
        }
    }

    public void setPool( Object[] obj){
        this.obj = obj;
        available = new boolean[obj.length];
        for (int i=0; i<available.length; i++){
            System.out.println("POOL " + obj[i].hashCode());
            available[i] = true;
        }
    }


    public Object getAvailableObj(){
        for(int i=0; i<obj.length; i++) {
            if( available[i] ){
                available[i] = false;
                System.out.println("Object " + i + " " + " was allocated " + obj[i].hashCode());
                return obj[i];
            }
        }
        System.out.println( " FAILED TO allocate !");

        return obj[0];
    }



    public boolean release(Object o){
        for(int i=0; i<obj.length; i++){
            if( obj[i] == o ){
                available[i] = true;
                System.out.println("Object " + i + " " + " was RELEASED!");
                return true;
            }
        }
        System.out.println( " FAILED TO RELEASE OBJECT " + o.hashCode());

        return false;
    }

}
