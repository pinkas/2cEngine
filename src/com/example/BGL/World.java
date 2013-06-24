package com.example.BGL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.graphics.Point;

import com.example.BGL.BglObject;

public class World {
	private List<BglObject> habitants;

	
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
	
	public void update() {

		Enumeration<BglObject> e = Collections.enumeration( habitants );
		
		while( e.hasMoreElements() ) {
			BglObject obj = e.nextElement();
			//update position
			
			Point pos = obj.posGet();
			Point speed = obj.speedGet();
			
			int angle_x = obj.angleGet(0);
			int angle_y = obj.angleGet(1);
			
			if (pos.x > 1200 || pos.x < 0 )
				speed.x = - speed.x;
			if (pos.y > 638 || pos.y < 0 )
				speed.y = - speed.y;
			
			pos.x = pos.x + speed.x;
			pos.y = pos.y + speed.y;
			//obj.zSet();
			
//			obj.angleSet( 0, angle_x%360 + 1 );
			obj.angleSet( 1, angle_y%360 + 2 );

			obj.posSet( pos );
			//update speed
			// do your shit here
		}
	}
}
