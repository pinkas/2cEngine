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
import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Bobject;

public class SceneManager {

    //TODO just keep the hashmap? I am not doing that for now because I'm guessing it's faster
    //to always iterate the List rather than the hashmap
    private Map< String, Scene> sceneHashMap;
    private volatile static SceneManager instance;
    private List<Scene> scenes;
    private Point screenSize;
    private RectF rectangle;
    private PointF camPos;
    private Scene focusScene;

    public SceneManager( Point screenSize ) {
        this.sceneHashMap = new HashMap< String, Scene>();
        this.scenes = new ArrayList<Scene>();
	    this.screenSize = screenSize;
        rectangle = new RectF();
        camPos = new PointF( 0.5f, 0.5f );
        /* TODO beurk */
        instance = this;
    }

    public static SceneManager getInstance(){
        if (instance == null){
            synchronized (BtextureManager.class){
                if (instance == null){
                    System.out.println( "No world created, please create one" );
                    //instance = new World();
                }
            }
        }
        return instance;
    }

    public void addScene( Scene s ){
        sceneHashMap.put( s.getName(), s);
        scenes.add(s);
    }

    public void setFocusScene( String sceneName ){
        //TODO checker si le nom de la scene est valide!!!!!!
        focusScene = sceneHashMap.get(sceneName);
    }

    public void setFocusScene(Scene scene){
        focusScene = scene;
    }

    public void startScene(String sceneName){
        //TODO checker si le nom de la scene est valide!!!!!!
        sceneHashMap.get(sceneName).start();
    }

    public void stopScene(String sceneName){
        //TODO checker si le nom de la scene est valide!!!!!!
        sceneHashMap.get(sceneName).stop();
    }


    public List<Scene> getScenes(){
        return scenes;
    }

    public void setCamPos (float x, float y){
        camPos.x = x;
        camPos.y = y;
    }

    public PointF getCamPos (){
        return camPos;
    }

	public void updateTouchStates() throws Exception {

        float touchRelX = InputStatus.getTouchX() / (float) screenSize.x;
        float touchRelY = InputStatus.getTouchY() / (float) screenSize.y;

        float touchAbsX = touchRelX + camPos.x - 0.5f;
        float touchAbsY = touchRelY + camPos.y - 0.5f;

		Enumeration<BglObject> e = Collections.enumeration( focusScene.getMembers() );
		while( e.hasMoreElements() ) {

			BglObject obj = e.nextElement();
            float x = obj.getPos().x - obj.anchorPointGet().x * obj.sizeGet().x;
            float y = obj.getPos().y - obj.anchorPointGet().y * obj.sizeGet().y;
            float w = obj.sizeGet().x;
            float h = obj.sizeGet().y;

            rectangle.set( x, y, x+w, y+h);

			// TODO
			// have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
                case DOWN:
                    if ( rectangle.contains( touchAbsX, touchAbsY ) ){
                        obj.touchDown();
                        break;
                    }
				case UP:
                    if ( rectangle.contains( touchAbsX, touchAbsY ) ){
                        obj.touchUp();
                        break;
                    }
                    else if( obj.isPressed() ){
                        obj.touchUpMove( touchAbsX, touchAbsY );
                        break;
                    }
				case MOVE:
					if ( rectangle.contains( touchAbsX, touchAbsY ) ) {
						obj.touchMove( touchAbsX, touchAbsY );
						break;
                    }
			}
		}
			InputStatus.resetTouch();
	}

    public void update() {
        enumarateAllMembers( new Brun<Void>() {
            public Void exec(BglObject obj) throws Exception {
                obj.update();
                return null;
            }
        });

        Enumeration<Scene> s = Collections.enumeration( scenes );
        while( s.hasMoreElements() ){
            Scene scene = s.nextElement();

            Enumeration<Bobject> e = Collections.enumeration( scene.getUpdateOnlyMembers() );
            while( e.hasMoreElements() ) {
                Bobject obj = e.nextElement();
                obj.update();

            }
        }
    }

    public void enumarateAllMembers( Brun<Void> cb ){
        Enumeration<Scene> s = Collections.enumeration( scenes );
        while( s.hasMoreElements() ){
            Scene scene = s.nextElement();

            Enumeration<BglObject> e = Collections.enumeration( scene.getMembers() );
            while( e.hasMoreElements() ) {
                BglObject obj = e.nextElement();

                try {
                    cb.exec(obj);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
