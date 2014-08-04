package com.example.bEngine;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Bobject;
import com.example.bEngine.scene.SceneManager;

public class InputStatus {

	public static enum TouchState{
		UP, DOWN, MOVE, NONE
	}
	
	private static TouchState touchState = TouchState.NONE;
	public static Point touch = new Point();
    public static Point touchPrev = new Point();
    public static Point touchDelta = new Point();
    private static RectF rectangle = new RectF();
	
	public static void setTouchDown(int x, int y) {
		touchState = TouchState.DOWN;
        touch.set(x,y);
	}

	public static void setTouchUp(int x, int y) {
		touchState = TouchState.UP;
        touch.set(x,y);
	}
	
	public static void setTouchMove(int x, int y) {
		touchState = TouchState.MOVE;
        touch.set(x,y);
	}

    public static void setTouchPrev(int x, int y){
        touchPrev.set(x,y);
    }

    public static void setTouchDelta(Point prev, Point curr){
        touchDelta.set(prev.x - curr.x, prev.y - curr.y);
    }
    public static Point getTouchDelta(){
        return touchDelta;
    }

	public static int getTouchX() {
		return touch.x;
	}
	
	public static int getTouchY() {
		return touch.y;
	}
	public static void resetTouch() {
		touchState = TouchState.NONE;
	}
	
	public static TouchState getTouchState() {
		return touchState;
	}
	
	public static void setTouchState( TouchState state ) {
		touchState = state;
	}



    public static void updateObjecTouchState(Bobject  obj, int screenW, int screenH   ) {
        float touchRelX = getTouchX() / (float) screenW;
        float touchRelY = getTouchY() / (float) screenH;

        float touchAbsX = touchRelX + Brenderer.getCamPosX() - 0.5f;
        float touchAbsY = touchRelY + Brenderer.getCamPosY() - 0.5f;


        float objX = obj.getPosX();
        float objY = obj.getPosY();
        float objW = obj.getSizeW();
        float objH = obj.getSizeH();
        rectangle.set(objX, objY, objX + objW, objY + objH);

        /** TODO have different list of objects, some are purely static
         / some are input sensitive, some are physics ... */
        switch (getTouchState()) {
            case DOWN:
                if (rectangle.contains(touchAbsX, touchAbsY)) {
                    obj.touchDown();
                    break;
                }
            case UP:
                if (rectangle.contains(touchAbsX, touchAbsY)) {
                    obj.touchUp();
                    break;
                } else if (obj.isPressed()) {
                    obj.touchUpMove(touchAbsX, touchAbsY);
                    break;
                }
            case MOVE:
                if (rectangle.contains(touchAbsX, touchAbsY)) {
                    obj.touchMove(touchAbsX, touchAbsY);
                    break;
                }
        }
    }

}
