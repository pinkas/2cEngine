package com.benpinkas.bEngine.scene;

import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.object.Bobject;

import java.util.ArrayList;

/**
 * Created by Ben on 1/30/14.
 */
public abstract class Scene {

    public boolean dirty;

    private BglObject[] members = new BglObject[0];
    private ArrayList <BglObject> membersArrayList;

    private ArrayList <Bobject> justUpdateMembers;

    private boolean visible = true;
    private String name;

    public Scene( String name ){
        membersArrayList = new ArrayList<BglObject>();
        justUpdateMembers = new ArrayList<Bobject>();
        this.name = name;
        SceneManager.addScene(this);
    }

    public void fillMembers(){
        int arraySize = membersArrayList.size();
        members = new BglObject[arraySize];
        // fill the members array
        int i=0;
        for (BglObject o : membersArrayList){
            members[i] = o;
            i++;
        }
    }

    public synchronized void add( Bobject obj){
        justUpdateMembers.add(obj);
    }

    public synchronized void add( BglObject obj){
        membersArrayList.add(obj);
        dirty = true;
    }

    public void add( BcontainerObject obj ){
        ArrayList<BglObject> child = obj.getChild();
        // Add all the BglObject childs to the scene
        for ( BglObject o : child ) {
            this.add(o);
        }
        // Add the container itself(to take advantage of its update method)
        justUpdateMembers.add(obj);
    }

    public ArrayList<BglObject> getMembersArrayList() {
        return membersArrayList;
    }

    public BglObject[] getMembers(){
        return members;
    }

    public ArrayList<Bobject> getUpdateOnly() {
        return justUpdateMembers;
    }

    public void setVisible( boolean visible ){
        this.visible = visible;
    }

    public boolean getVisible(){
        return visible;
    }

    public String getName(){
        return name;
    }

    public abstract void start();

    public abstract void stop();

    public void update(float dt){};

}
