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
	
	public static Point getTouch() {
		return touch;
	}

	public static void setTouch( int x, int y ) {
		touchPrev.y = touch.x;
		touchPrev.y = touch.y;
	
		touch.x = x;
		touch.y = y;
	}	
	
	public void update() {

		Enumeration<BglObject> e = Collections.enumeration( habitants );
		
		while( e.hasMoreElements() ) {
			BglObject obj = e.nextElement();
			
			Point pos = obj.posGet();
			Point speed = obj.speedGet();
			int angle_y = obj.angleGet(1);
			
			// Is this fellow object finger touched ?
			if ( obj.rectangle.contains( touch.x, touch.y ) ) {
				
				//System.out.println( "=================>touched!!!!!" );
				speed.x = speed.x + 10;
			}
			
				
			// Friction yo
			if (speed.x > 0) {
				speed.x -= 1;
				if (speed.x<0) {
					speed.x = 0;
				}
			}
			
			// Update position			
			if (pos.x > 1200 || pos.x < 0 )
				speed.x = - speed.x;
			if (pos.y > 638 || pos.y < 0 )
				speed.y = - speed.y;
			
			pos.x = pos.x + speed.x;
			pos.y = pos.y + speed.y;
			//obj.zSet();
			
			obj.angleSet( 1, angle_y%360 + 2 );

			obj.posSet( pos );
			//update speed
			// do your shit here
		}
		
		touch.x = 3000;
		touch.y = 3000;
	}


}
