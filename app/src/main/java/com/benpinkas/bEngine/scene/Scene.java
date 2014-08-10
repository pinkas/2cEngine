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

    private ArrayList <BglObject> membersToRemove;
    private ArrayList <BglObject> membersToAdd;

    //TODO BEN LOOK!!!!!!!!!! fix that it's ugly to have 2 list like that ? There MIGHT be another way
    private ArrayList <Bobject> justUpdateMembers; /* Members that don't get rendered */

    private boolean visible = true;
    private String name;

    public Scene( String name ){
        membersArrayList = new ArrayList<BglObject>();
        membersToRemove = new ArrayList<BglObject>();
        justUpdateMembers = new ArrayList<Bobject>();
        membersToAdd = new ArrayList<BglObject>();
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

    public void addAsync(BglObject obj){
        membersToAdd.add(obj);
    }

    public synchronized void add( Bobject obj){
        justUpdateMembers.add(obj);
    }

    public synchronized void add( BglObject obj){
        membersArrayList.add(obj);
        dirty = true;
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

    //TODO this only set on top
    //TODO broken
/*-
    public void setMemberLayer( BglObject obj, int layer){
        int i = 0;
        while (members[i] != obj){
            i++;
        }
        members[i] = null;
    }
*/
    public BglObject[] getMembers(){
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

    public void update(float dt){};
    // world show scene
    // the objects are render and taken into account in the world

    //a scene is a list of object

//    create_scene( menu )
// pass a cb_function what to do when a some event happens
    // dans le cas du menu quand tu presse tel bouton on kill menu scene et start game scene
}
