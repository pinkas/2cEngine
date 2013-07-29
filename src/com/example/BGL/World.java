package com.example.BGL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.graphics.Point;

import com.example.BGL.BglObject;
import com.example.helloben.Table;

public class World {
	private List<BglObject> habitants;
    static private volatile Point touch = new Point(3000,3000);
    static private Point touchPrev = new Point(3000,300);


    Table myTable = new Table();

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


			// TODO have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
                case DOWN:
                    if ( obj.rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY()))
                        obj.touchDown();
                    break;
				case UP:
                    if (obj.rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY())){
                        obj.touchUp();
                        break;
                    }
                    else if(obj.isPressed()){
                        obj.touchUpMove(InputStatus.getTouchX(), InputStatus.getTouchY());
                        break;
                    }

				case MOVE:
					if ( obj.rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY()))
						obj.touchMove();
						break;
			}


            if (!obj.rectangle.intersect(myTable.perim)){
//                Point blah = new Point(0,0);
                obj.speed.x = - obj.speed.x;
                obj.speed.y = - obj.speed.y;
  //              obj.speedSet( blah );

            }



/*
            if (obj.isPressed())
                System.out.println("PRESSED");
            else
                System.out.println("NOT PRESSED!");
*/
            obj.update();

		}

			InputStatus.resetTouch();
	}


}
