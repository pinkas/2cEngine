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


    public static synchronized void update( float dt ) {

        /* Update rendered object */
        for (Scene scene : scenes)
        {
            for ( BglObject obj : scene.getMembers()  )
            {
                obj.update( dt );

                /*// TODO Have a function that should determine if the obj should be removed from the table
                if (obj.getPos().x < -0.1){
                    //scene.addToRemove(obj);
                }
                */
                if ( true ){
                    continue;
                }

                if (!obj.isVisible())
                    continue;

                if (!obj.isCollide())
                    continue;

                //TODO Remove that
                if (obj instanceof Joypad)
                    continue;

                //TODO les 7 lignes suivantes peuvent etre factorises
                PointF pos = obj.getPos();
                PointF size = obj.getSize();
                PointF anchor = obj.anchorPointGet();
                float x = pos.x - anchor.x * size.x;
                float y = pos.y - anchor.y * size.y;
                float w = size.x;
                float h = size.y;

                rectangle.set(x, y, x+w, y+h);
                for( BglObject obj2 : scene.getMembers() )
                {

                    if (!obj2.isCollide())
                        continue;

                    if (!obj2.isVisible())
                        continue;

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
                    if ( obj != obj2 &&  (rectangle.intersect(rectangle2)  ) ){
                        obj.collision();
                    }
                }
            }
            for ( Bobject obj : scene.getUpdateOnlyMembers()  )
            {
                obj.update(dt);
            }
            scene.fromMembersToAddToMembers();
        }
/*
        // Remove Objects to remove
        for (Scene scene : scenes) {
            for (BglObject obj : scene.getMembersToRemove()){
                scene.getMembers().remove(obj);
                System.out.println( "remve  " + obj );
            }
            scene.getMembersToRemove().clear();
        }
*/
    }
}
