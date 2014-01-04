package com.example.bEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.bEngine.object.BglObject;
import com.example.bEngine.object.Brectangle;
import com.example.bEngine.shader.Shader;
import com.example.bEngine.shader.ShaderList;

public  class World {

	private List<BglObject> habitants;
    private Point screenSize;
    private RectF rectangle;
    private PointF camPos;

    public World( Point screenSize ) {
		this.habitants = new ArrayList<BglObject>();
	    this.screenSize = screenSize;
        rectangle = new RectF();
        camPos = new PointF( 0.5f, 0.5f );
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


    public void setCamPos (float x, float y){
        camPos.x = x;
        camPos.y = y;
    }

    /* Load textures */
    public void loadObjectTexture(Context context, ShaderList sl){
        Enumeration<BglObject> e = Collections.enumeration(habitants);

        while( e.hasMoreElements() ) {
            BglObject obj = e.nextElement();
            final Shader shader =  sl.getProg( obj.getShaderName() );
            obj.loadTexture(context, shader);
        }
    }


	public void updateTouchStates() {

		Enumeration<BglObject> e = Collections.enumeration( habitants );
		while( e.hasMoreElements() ) {

			BglObject obj = e.nextElement();

            float x = obj.posGet().x - obj.anchorPointGet().x * obj.sizeGet().x;
            float y = obj.posGet().y - obj.anchorPointGet().y * obj.sizeGet().y;
            float w = obj.sizeGet().x;
            float h = obj.sizeGet().y;

            rectangle.set( x, y, x+w, y+h);

            System.out.println( x + "    " + y );

            float touchRelX = InputStatus.getTouchX() / (float) screenSize.x;
            float touchRelY = InputStatus.getTouchY() / (float) screenSize.y;

            float touchAbsX = touchRelX + camPos.x - 0.5f ;
            float touchAbsY = touchRelY + camPos.y - 0.5f ;

			// TODO
			// have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
                case DOWN:

                    if ( rectangle.contains( touchAbsX, touchAbsY ) ){
                        System.out.println( "TEST" );
                        obj.touchDown();
                        break;
                    }
				case UP:
                    if ( rectangle.contains( touchAbsX, touchAbsY ) ){
                        obj.touchUp();
                        break;
                    }
                    else if( obj.isPressed() ){
                        obj.touchUpMove( touchAbsX, touchAbsY );
                        break;
                    }
				case MOVE:
					if ( rectangle.contains( touchAbsX, touchAbsY ) ) {
						obj.touchMove( touchAbsX, touchAbsY );
						break;
                    }
			}

		}
			InputStatus.resetTouch();
	}




    public void update() {
        Enumeration<BglObject> e = Collections.enumeration( habitants );
        while( e.hasMoreElements() ) {
            BglObject obj = e.nextElement();
            // TODO have different list of objects, some are purely static
            // some are input sensitive, some are physics ...
            obj.update();
        }
    }




}
