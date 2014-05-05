package com.example.bEngine.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.bEngine.Brun;
import com.example.bEngine.BtextureManager;
import com.example.bEngine.InputStatus;
import com.example.bEngine.Joypad;
import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Bobject;

import junit.framework.Assert;

public class SceneManager {

    //TODO just keep the hashmap? I am not doing that for now because I'm guessing it's faster
    //to always iterate the List rather than the hashmap
    private static Map< String, Scene> sceneHashMap = new HashMap< String, Scene>();;
    private volatile static SceneManager instance;
    private static List<Scene> scenes = new ArrayList<Scene>();
    private final static RectF rectangle = new RectF();
    private final static RectF rectangle2 = new RectF();
    private PointF camPos = new PointF( 0.5f, 0.5f ); ;
    private static Scene focusScene;

    public static SceneManager getInstance(){
        if (instance == null){
            synchronized (SceneManager.class){
                if (instance == null){
                    instance = new SceneManager();
                }
            }
        }
        return instance;
    }

    public static boolean sceneExist (String sceneName) {
        return sceneHashMap.containsKey(sceneName);
    }

    public void addScene( Scene s ){
        sceneHashMap.put( s.getName(), s);
        scenes.add(s);
    }

    public static void setFocusScene( String sceneName ) {
        if (sceneExist(sceneName) ) {
            focusScene = sceneHashMap.get(sceneName);
        }
        else{
            System.out.println("Scene does not exist, WARNING");
        }
    }

    public static  void setFocusScene(Scene scene){
        focusScene = scene;
    }

    public static void startScene(String sceneName){
        if (sceneExist(sceneName)){
            sceneHashMap.get(sceneName).start();
        }
        else{
            System.out.println("Scene does not exist, WARNING");
        }
    }

    public static Scene getFocusScene(){
        return focusScene;
    }

    public static void stopScene(String sceneName){
        if ( sceneExist(sceneName) ) {
            sceneHashMap.get(sceneName).stop();
        }
        else{
            System.out.println("Scene does not exist, WARNING");
        }
    }

    public static List<Scene> getScenes(){
        return scenes;
    }

    public void setCamPos (float x, float y){
        camPos.x = x;
        camPos.y = y;
    }

    public PointF getCamPos (){
        return camPos;
    }

    public static synchronized void update() {

        /* Update rendered object */
        for (Scene scene : scenes) {
            System.out.println(scene.getMembers().size());
            for ( BglObject obj : scene.getMembers()  ) {

                obj.update();

                // TODO Have a function that should determine if the obj should be removed from the table
                if (obj.getPos().x < -0.1){
       //             scene.addToRemove(obj);
                }


            }
        }

        /* Remove Objects to remove */
        for (Scene scene : scenes) {
            for (BglObject obj : scene.getMembersToRemove()){
                scene.getMembers().remove(obj);
                System.out.println( "remve  " + obj );
            }
            scene.getMembersToRemove().clear();
        }

        /* Deal with collisions */
        for (Scene scene : scenes) {
            for ( BglObject obj : scene.getMembers()  ) {

                //TODO Remove that
                if (obj instanceof Joypad)
                    continue;

                PointF pos = obj.getPos();
                PointF size = obj.getSize();
                float x = pos.x - obj.anchorPointGet().x * size.x;
                float y = pos.y - obj.anchorPointGet().y * size.y;
                float w = size.x;
                float h = size.y;

                rectangle.set(x, y, x+w, y+h);
                for(BglObject obj2 : scene.getMembers()){

                    //TODO Remove that
                    if (obj2 instanceof Joypad)
                        continue;


                    pos = obj2.getPos();
                    size = obj2.getSize();
                    x = pos.x - obj.anchorPointGet().x * size.x;
                    y = pos.y - obj.anchorPointGet().y * size.y;
                    w = size.x;
                    h = size.y;
                    rectangle2.set(x, y, x+w, y+h);
                    if ( (rectangle.intersect(rectangle2) && obj != obj2) && obj2.isVisible() ){
                        obj.collision();
                        //System.out.println("====>Collision!" +"   " +  obj + " with " +  obj2 );
                    }
                }
            }
        }

        /* Update non-rendered objects */
        for (Scene scene : scenes) {
            for ( Bobject obj : scene.getUpdateOnlyMembers()  ) {
                obj.update();
            }
            scene.fromMembersToAddToMembers();
        }
    }

}
