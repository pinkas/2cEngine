package com.benpinkas.bEngine;

import android.graphics.Point;
import android.graphics.RectF;

import com.benpinkas.bEngine.object.Bobject;

public class InputStatus {

	public static enum TouchState{
		UP, DOWN, MOVE, NONE
	}
	
	private static TouchState touchState = TouchState.NONE;
	public static int touchXscr;
    public static int touchYscr;

    public static float touchXabsPerc;
    public static float touchYabsPerc;

    public static float touchPrevX;
    public static float touchPrevY;

    public static float touchDeltaX;
    public static float touchDeltaY;

    private static RectF rectangle = new RectF();
	
	public static void setTouchDown(int x, int y) {
		touchState = TouchState.DOWN;
        touchXscr = x;
        touchYscr = y;
	}

	public static void setTouchUp(int x, int y) {
		touchState = TouchState.UP;
        touchXscr = x;
        touchYscr = y;
	}
	
	public static void setTouchMove(int x, int y) {
		touchState = TouchState.MOVE;
        touchXscr = x;
        touchYscr = y;
	}

    public static void setTouchPrev(int x, int y){
        touchPrevX = x;
        touchPrevY = y;
    }

    public static void setTouchDelta(){
        touchDeltaX = touchPrevX - touchXscr;
        touchDeltaY = touchPrevY - touchYscr;
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

    public static void updateObjecTouchState(Bobject  obj, int screenW, int screenH) {
        float touchRelX = touchXscr / (float) screenW;
        float touchRelY = touchYscr / (float) screenH;

        touchXabsPerc = touchRelX + Brenderer.getCamPosX() - 0.5f;
        touchYabsPerc = touchRelY + Brenderer.getCamPosY() - 0.5f;

        float objX = obj.getPosX();
        float objY = obj.getPosY();
        float objW = obj.getSizeW();
        float objH = obj.getSizeH();
        rectangle.set(objX, objY, objX + objW, objY + objH);

        /** TODO have different list of objects, some are purely static
         / some are input sensitive, some are physics ... */
        switch (getTouchState()) {
            case DOWN:
                if (rectangle.contains(touchXabsPerc, touchYabsPerc)) {
                    obj.touchDown();
                    break;
                }
            case UP:
                if (rectangle.contains(touchXabsPerc, touchYabsPerc)) {
                    obj.touchUp();
                    break;
                } else if (obj.isPressed()) {
                    obj.touchUpMove(touchXabsPerc, touchYabsPerc);
                    break;
                }
            case MOVE:
                if (rectangle.contains(touchXabsPerc, touchYabsPerc)) {
                    obj.touchMove(touchXabsPerc, touchYabsPerc);
                    break;
                }
        }
    }

}
