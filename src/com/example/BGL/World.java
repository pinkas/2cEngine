package com.example.BGL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.example.BGL.object.BglObject;
import com.example.BGL.object.Brectangle;
import com.example.BGL.shader.Shader;
import com.example.BGL.shader.ShaderList;

public  class World {

	private List<BglObject> habitants;
    private Point screenSize;
    private Rect rectangle;
    private PointF camPos;

    public World( Point screenSize ) {
		this.habitants = new ArrayList<BglObject>();
	    this.screenSize = screenSize;
        rectangle = new Rect();
        camPos = new PointF( 0,0 );
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


    public void MOCHE_camera (float x, float y){
        camPos.x = x;
        camPos.y = y;
    }

    // Load textures and Shaders
    public void loadObjectTexture(Context context, ShaderList sl){
        Enumeration<BglObject> e = Collections.enumeration(habitants);

        while( e.hasMoreElements() ) {
            BglObject obj = e.nextElement();
            /* TODO UGLY FIX ME!!!!!!!! */
            if ( obj instanceof Brectangle){
                obj.initShader(sl.rectangleShader);
                continue;
            }
            obj.initShader(sl.basicShader);
            obj.loadTexture(context);
        }
    }


    public void loadObjectShader(Shader shader){
        Enumeration<BglObject> e = Collections.enumeration( habitants );

        while( e.hasMoreElements() ) {
            BglObject obj = e.nextElement();
            System.out.println(shader);
        }
    }


	public void updateTouchStates() {

		Enumeration<BglObject> e = Collections.enumeration( habitants );
		while( e.hasMoreElements() ) {

			BglObject obj = e.nextElement();

            if (obj.getBoundToCamera()){
                camPos.x = obj.posGet().x - obj.anchorPointGet().x * obj.sizeGet().x;
                camPos.y = obj.posGet().y - obj.anchorPointGet().y * obj.sizeGet().y;
            }

            int x = (int) ( (obj.posGet().x - obj.anchorPointGet().x * obj.sizeGet().x  ) * screenSize.x);
            int y = (int) ( (obj.posGet().y - obj.anchorPointGet().y * obj.sizeGet().y ) * screenSize.y);
            int w = (int) ( obj.sizeGet().x * screenSize.x);
            int h = (int) ( obj.sizeGet().y * screenSize.y);

            rectangle.set( x, y, x + w, y + h);

			// TODO have different list of objects, some are purely static
			// some are input sensitive, some are physics ...
			switch ( InputStatus.getTouchState() ) {
                case DOWN:

                    if ( rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY() )){
                        obj.touchDown();
                        break;
                    }
				case UP:
                    if ( rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY())){
                        obj.touchUp();
                        break;
                    }
                    else if(obj.isPressed()){
                        obj.touchUpMove(  (float) InputStatus.getTouchX() / (float) screenSize.x, (float) InputStatus.getTouchY() / (float) screenSize.y );
                        break;
                    }
				case MOVE:
					if ( rectangle.contains(InputStatus.getTouchX(), InputStatus.getTouchY()))
						obj.touchMove( (float) InputStatus.getTouchX() / (float) screenSize.x, (float) InputStatus.getTouchY() / (float) screenSize.y);
						break;
			}

          ///  obj.update();
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
