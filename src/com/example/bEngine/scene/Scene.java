package com.example.bEngine.scene;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Bobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 1/30/14.
 */
public  class Scene {

    private ArrayList <BglObject> members;
    //TODO fix that it's ugly to have 2 list like that ? There MIGHT be another way
    private ArrayList <Bobject> justUpdateMembers;

    private boolean visible = true;
    private String name;

    public Scene( String name ){
        members = new ArrayList<BglObject>();
        justUpdateMembers = new ArrayList<Bobject>();
        this.name = name;
        SceneManager.getInstance().addScene(this);
    }

    public void add( BglObject obj){
        members.add( members.size(), obj);
    }

    public void add( Bobject obj){
        justUpdateMembers.add(obj);
    }
    public List<Bobject> getUpdateOnlyMembers(){
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

    public  void start(){
    }

    public void stop(){
    }

    // world show scene
    // the objects are render and taken into account in the world

    //a scene is a list of object

//    create_scene( menu )
// pass a cb_function what to do when a some event happens
    // dans le cas du menu quand tu presse tel bouton on kill menu scene et start game scene
}
