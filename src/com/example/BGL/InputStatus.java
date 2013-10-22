package com.example.BGL;

import android.graphics.Point;

public class InputStatus {

	public static enum TouchState{
		UP, DOWN, MOVE, NONE
	}
	
	private static TouchState touchState = TouchState.NONE;
	private static Point touch = new Point(); 
	
	public InputStatus() {	
	}
	
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
	
}
