package com.example.BGL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.graphics.Point;

import com.example.BGL.BglObject;

public class World {
	private List<BglObject> habitants;
    static private volatile Point touch = new Point(3000,3000);
    static private Point touchPrev = new Point(3000,300);
    
	
	public World() {
		this.habitants = new ArrayList<BglObject>();
	}
	
	public List<BglObject> getHabitants() {
		return habitants;
	}
	
	public void addHabitant( BglObject obj ) {
		habitants.add(obj);
	}
	
	public void removeHabitant( BglObject obj) {
		habitants.remove(obj);
	}
	/*
	public static Point getTouch() {
		return touch;
	}

	public static void setTouchDown( int x, int y ) {
		touchPrev.x = touch.x;
		touchPrev.y = touch.y;
		touch.x = x;
		touch.y = y;
	}
	
	public static void setTouchUp( int x, int y ) {
		touchPrev.x = touch.x;
		touchPrev.y = touch.y;
		touch.x = x;
		touch.y = y;
	}
	
	public static void setTouchMove( int x, int y ) {
		touchPrev.x = touch.x;
		touchPrev.y = touch.y;
		touch.x = x;
		touch.y = y;
	}
	*/
	public void update() {

		Enumeration<BglObject> e = Collections.enumeration( habitants );
		
		while( e.hasMoreElements() ) {
			BglObject obj = e.nextElement();

            obj.update();

			// TODO have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
				case UP:
					if (obj.rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY()))
						obj.touchUp();
						break;
				case DOWN:
					if ( obj.rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY()))
						obj.touchDown();
						break;
				case MOVE:
					if ( obj.rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY()))
						obj.touchMove();
						break;
			}

		}

			InputStatus.resetTouch();

	}


}
