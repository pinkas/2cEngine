package com.example.bEngine;

import android.graphics.RectF;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Brectangle;

import java.lang.annotation.Retention;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by Ben on 2/2/14.
 */

public class InputDispatcher {

    private RectF rect = new RectF();

    public InputDispatcher(){
    }

   	public void updateTouchStates() {
/*
		Enumeration<BglObject> e = Collections.enumeration(focusScene.getMembers());
		while( e.hasMoreElements() ) {

			BglObject obj = e.nextElement();

            float x = obj.posGet().x - obj.anchorPointGet().x * obj.sizeGet().x;
            float y = obj.posGet().y - obj.anchorPointGet().y * obj.sizeGet().y;
            float w = obj.sizeGet().x;
            float h = obj.sizeGet().y;

            rect.set(x, y, x+w, y+h);

            float touchRelX = InputStatus.getTouchX() / (float) screenSize.x;
            float touchRelY = InputStatus.getTouchY() / (float) screenSize.y;

            float touchAbsX = touchRelX + camPos.x - 0.5f;
            float touchAbsY = touchRelY + camPos.y - 0.5f;

			// TODO
			// have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
                case DOWN:

                    if ( rect.contains( touchAbsX, touchAbsY ) ){
                        obj.touchDown();
                        break;
                    }
				case UP:
                    if ( rect.contains( touchAbsX, touchAbsY ) ){
                        obj.touchUp();
                        break;
                    }
                    else if( obj.isPressed() ){
                        obj.touchUpMove( touchAbsX, touchAbsY );
                        break;
                    }
				case MOVE:
					if ( rect.contains( touchAbsX, touchAbsY ) ) {
						obj.touchMove( touchAbsX, touchAbsY );
						break;
                    }
			}
		}
			InputStatus.resetTouch();
			*/
	}

}
