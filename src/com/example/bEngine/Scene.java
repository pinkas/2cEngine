package com.example.bEngine;

import android.R;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Bobject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Ben on 1/30/14.
 */
public  class Scene {

    private ArrayList <BglObject> members;
    private boolean visible = true;
    private String name;

    public Scene( String name ){
        members = new ArrayList<BglObject>();
        this.name = name;
        SceneManager.getInstance().addScene(this);
    }

    public void add( BglObject obj){
        members.add( members.size(), obj);
    }

    public void setMemberLayer(  BglObject obj, int layer){
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

    public void stop( ){
    }

    // world show scene
    // the objects are render and taken into account in the world

    //a scene is a list of object

//    create_scene( menu )
// pass a cb_function what to do when a some event happens
    // dans le cas du menu quand tu presse tel bouton on kill menu scene et start game scene
}
