package com.benpinkas.bEngine.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.RectF;


import com.benpinkas.bEngine.object.BglObject;
import com.benpinkas.bEngine.object.Btimer;

public class SceneManager {

    //TODO just keep the hashmap? I am not doing that for now because I'm guessing it's faster
    //to always iterate the List rather than the hashmap
    private static Map<String, Scene> sceneHashMap = new HashMap<String, Scene>();
    ;
    private static List<Btimer> btimers = new ArrayList<Btimer>();
    private static List<Scene> scenes = new ArrayList<Scene>();
    private final static RectF rectangle = new RectF();
    private final static RectF rectangle2 = new RectF();
    private static Scene focusScene;

    private static List<BglObject> area1 = new ArrayList<BglObject>();
    private static List<BglObject> area2 = new ArrayList<BglObject>();
    private static List<BglObject> area3 = new ArrayList<BglObject>();
    private static List<BglObject> area4 = new ArrayList<BglObject>();
    private static List<BglObject> area5 = new ArrayList<BglObject>();
    private static List<BglObject> area6 = new ArrayList<BglObject>();

    public enum collisionSide {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }

    public static boolean sceneExist(String sceneName) {
        return sceneHashMap.containsKey(sceneName);
    }

    public static void addTimer(Btimer btimer) {
        btimers.add(btimer);
    }

    /**
     * Add a scene to the hasmap list
     *
     * @param s reference to the scene to add
     */
    public static void addScene(Scene s) {
        sceneHashMap.put(s.getName(), s);
        scenes.add(s);
    }

    /**
     * Give input focus to a scene
     * <p></p>
     * A scene with the focus will pass touch events to its objects
     *
     * @param sceneName name of the scene you want to give the focus to.
     */
    public static void setInputFocus(String sceneName) {
        if (sceneExist(sceneName)) {
            focusScene = sceneHashMap.get(sceneName);
        } else {
            System.out.println("Scene does not exist, WARNING");
        }
    }

    /**
     * Give input focus to a scene
     * <p></p>
     * A scene with the focus will pass touch events to its objects
     *
     * @param scene reference of the scene you want to give the focus to.
     */
    public static void setInputFocus(Scene scene) {
        focusScene = scene;
    }

    /**
     * get reference to the scene that has the input focus
     *
     * @return the scene that has the input focus
     */
    public static Scene getInputFocus() {
        return focusScene;
    }

    /**
     * Execute the start method of a scene
     *
     * @param sceneName name of the scene you want to start.
     */
    public static void startScene(String sceneName) {
        if (sceneExist(sceneName)) {
            sceneHashMap.get(sceneName).start();
        } else {
            System.out.println("Scene does not exist, WARNING");
        }
    }


    /**
     * Execute the stop method of a scene
     *
     * @param sceneName name of the scene you want to stop.
     */
    public static void stopScene(String sceneName) {
        if (sceneExist(sceneName)) {
            sceneHashMap.get(sceneName).stop();
        } else {
            System.out.println("Scene does not exist, WARNING");
        }
    }

    /**
     * Get the the hashmap scene of all the scenes
     *
     * @return hasmap list of scenes
     */
    public static List<Scene> getScenes() {
        return scenes;
    }

    public static void updateTimers(float dt){
        for (Btimer t : btimers) {
            t.update(dt);
        }
    }
}