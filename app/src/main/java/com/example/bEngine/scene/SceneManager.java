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

//TODO about this class"
// Main thing to fix:
// C'est un singleton donc pas possible de passer de parametres d'entrees, hors, j'ai besoin de passer
// la taille de l'ecran au sceneManager car le sceneManager contient la methode updateTouchStates
// ce que est debile, ca devrait etre dans une classe a part. Je devrais creer une classe "Input" ou
// quelque chose du genre.


public class SceneManager {

    //TODO just keep the hashmap? I am not doing that for now because I'm guessing it's faster
    //to always iterate the List rather than the hashmap
    private Map< String, Scene> sceneHashMap;
    private volatile static SceneManager instance;
    private List<Scene> scenes;
    private Point screenSize;
    private final RectF rectangle;
    private final RectF rectangle2;
    private PointF camPos;
    private Scene focusScene;

    public SceneManager( Point screenSize ) {
        this.sceneHashMap = new HashMap< String, Scene>();
        this.scenes = new ArrayList<Scene>();
	    this.screenSize = screenSize;
        System.out.println(screenSize.x+ "  @@@@@@@@@@@@@@@ "  + screenSize.y );
        rectangle = new RectF();
        rectangle2 = new RectF();
        camPos = new PointF( 0.5f, 0.5f );
        /* TODO beurk */
        instance = this;
    }

    public static SceneManager getInstance(){
        if (instance == null){
            synchronized (SceneManager.class){
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
        //TODO probleme rencontre une fois, DO IT DO IT DO IT!!!!!
        //Verifier que la scene existe
        //Verifier que la methode start existe
        //Verifier surement autre chose.
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

	public synchronized void updateTouchStates() {

        float touchRelX = InputStatus.getTouchX() / (float) screenSize.x;
        float touchRelY = InputStatus.getTouchY() / (float) screenSize.y;

        float touchAbsX = touchRelX + camPos.x - 0.5f;
        float touchAbsY = touchRelY + camPos.y - 0.5f;

		Enumeration<BglObject> e = Collections.enumeration( focusScene.getMembers() );
		while( e.hasMoreElements() ) {

			BglObject obj = e.nextElement();

            PointF pos = obj.getPos();
            PointF size = obj.getSize();

            float x = pos.x - obj.anchorPointGet().x * size.x;
            float y = pos.y - obj.anchorPointGet().y * size.y;
            float w = size.x;
            float h = size.y;

            rectangle.set( x, y, x+w, y+h);

			// TODO
			// have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
                case DOWN:
                    if ( rectangle.contains( touchAbsX, touchAbsY ) ){
                        obj.touchDown();
                        //break;
                    }
				case UP:
                    if ( rectangle.contains( touchAbsX, touchAbsY ) ){
                        obj.touchUp();
                        //break;
                    }
                    else if( obj.isPressed() ){
                        obj.touchUpMove( touchAbsX, touchAbsY );
                        //break;
                    }
				case MOVE:
					if ( rectangle.contains( touchAbsX, touchAbsY ) ) {
						obj.touchMove( touchAbsX, touchAbsY );
						//break;
                    }
			}
		}
	    InputStatus.resetTouch();
	}

    public synchronized void update() {

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
                    if ( rectangle.intersect(rectangle2) && obj != obj2){
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
