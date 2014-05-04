package com.example.bEngine.scene;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Bobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Ben on 1/30/14.
 */
public abstract  class Scene {

    private ArrayList <BglObject> members;
    private ArrayList <BglObject> membersToRemove;
    private ArrayList <BglObject> membersToAdd;

    //TODO BEN LOOK!!!!!!!!!! fix that it's ugly to have 2 list like that ? There MIGHT be another way
    private ArrayList <Bobject> justUpdateMembers; /* Members that don't get rendered */

    private boolean visible = true;
    private String name;

    public Scene( String name ){
        members = new ArrayList<BglObject>();
        membersToRemove = new ArrayList<BglObject>();
        justUpdateMembers = new ArrayList<Bobject>();
        membersToAdd = new ArrayList<BglObject>();
        this.name = name;
        SceneManager.getInstance().addScene(this);
    }



    public void addAsync(BglObject obj){
        membersToAdd.add(obj);
    }

    public synchronized void add( Bobject obj){
        justUpdateMembers.add(obj);
    }

    public synchronized void add( BglObject obj){
        // So when adding an object to an arraylist with empty slot
        // The objecty will always be at the end of the array therefor
        // on the foreground of the Scene
        members.add( members.size(), obj);
    }

    public void addToRemove ( BglObject obj ){
        membersToRemove.add( obj );
    }

    public ArrayList<BglObject> getMembersToRemove (){
        return membersToRemove;
    }

    public void fromMembersToAddToMembers(){
        for ( BglObject obj : membersToAdd )
        {
            add(obj);
        }
        membersToAdd.clear();
    }

    public ArrayList<Bobject> getUpdateOnlyMembers(){
        return justUpdateMembers;
    }

    public void setMemberLayer( BglObject obj, int layer){
        members.remove(obj);
        members.add( layer, obj);
    }

    public List<BglObject> getMembers(){
        return members;
    }

    public void setVisible( boolean visible ){
        this.visible = visible;
    }

    public boolean getVisible(){
        return visible;
    }

    String getName(){
        return name;
    }

    public abstract void start();

    public abstract void stop();

    // world show scene
    // the objects are render and taken into account in the world

    //a scene is a list of object

//    create_scene( menu )
// pass a cb_function what to do when a some event happens
    // dans le cas du menu quand tu presse tel bouton on kill menu scene et start game scene
}
