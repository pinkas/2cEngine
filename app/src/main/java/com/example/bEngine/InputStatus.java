package com.example.bEngine;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.scene.SceneManager;

public class InputStatus {

	public static enum TouchState{
		UP, DOWN, MOVE, NONE
	}
	
	private static TouchState touchState = TouchState.NONE;
	public static Point touch = new Point();
    private static RectF rectangle = new RectF();
	
	public static void setTouchDown(int x, int y) {
		touchState = TouchState.DOWN;
		touch.x = x;
		touch.y = y;
	}

	public static void setTouchUp(int x, int y) {
		touchState = TouchState.UP;
		touch.x = x;
		touch.y = y;
	}
	
	public static void setTouchMove(int x, int y) {
		touchState = TouchState.MOVE;
		touch.x = x;
		touch.y = y;
	}	
	
	public static int getTouchX() {
		return touch.x;
	}
	
	public static int getTouchY() {
		return touch.y;
	}
	public static void resetTouch() {
		touchState = TouchState.NONE;
		touch.x = 3000;
		touch.y = 3000;
	}
	
	public static TouchState getTouchState() {
		return touchState;
	}
	
	public static void setTouchState( TouchState state ) {
		touchState = state;
	}


    public static synchronized void updateTouchStates( int screenW, int screenH ) {

        float touchRelX = getTouchX() / (float) screenW;
        float touchRelY = getTouchY() / (float) screenH;

        //float touchAbsX = touchRelX + camPos.x - 0.5f;
        //float touchAbsY = touchRelY + camPos.y - 0.5f;

        float touchAbsX = touchRelX ;
        float touchAbsY = touchRelY ;

        //TODO avoir un methode qui retourne les membes de la focus scene
        for ( BglObject obj : SceneManager.getInstance().getFocusScene().getMembers() )
        {
            PointF pos = obj.getPos();
            PointF size = obj.getSize();
            PointF anchor = obj.anchorPointGet();

            float x = pos.x - anchor.x * size.x;
            float y = pos.y - anchor.y * size.y;
            float w = size.x;
            float h = size.y;

            rectangle.set( x, y, x+w, y+h);

            // TODO
            // have different list of objects, some are purely static
            // some are input sensitive, some are physics ...
            switch ( getTouchState() ) {
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


}
