package com.example.helloben;

import android.graphics.Point;

import com.example.bEngine.object.Brectangle;

/**
 * Created by Ben on 10/14/13.
 */
public class RectangleRotateGrid {

    private RectangleRotate[] rect;

    public RectangleRotateGrid( int x, int y, int col, int row ){

        rect = new RectangleRotate[ col * row ];
        int index = 0;
        for( int i=0 ; i<row; i++){
            for( int j=0; j<col;j++){

                rect[index] = new RectangleRotate( x + 101 * j , y + 101 * i, 100, 100, 0.0f, 0.0f, 0.0f, 1.0f);
                index ++;
            }
        }


    }

    public RectangleRotate getRects( int index ){
        return rect[index];
    }

}


class RectangleRotate extends Brectangle {

    private float [] angleVel = new float [3];
    private Point prevPos;


    public RectangleRotate(int x, int y, int w, int h, float r, float g, float b, float a) {
        super(x, y, w, h, r, g, b, a);

        angleVel[0] = 0;
        angleVel[1] = 0;
        angleVel[2] = 0;

        prevPos = new Point(x,y);
        prevPos.x = 0;
        prevPos.y = 0;

    }

    // Finger touches the object
    public void touchDown() {
        super.touchDown();
        //    setColor(0.2f,0.4f,0.5f,1.0f);
    }
    // Finger realeases when over the object
    public void touchUp() {
        super.touchUp();
        //    setColor(0.6f,0.7f,1.0f,1.0f);

    }
    //When moving your finger over the object
    public void touchMove(int x, int y) {

        angleVel[1] = (x - prevPos.x)/2;
        angleVel[2] = (y - prevPos.y)/2;

        if(prevPos.x == 0){
            angleVel[1] = 0;
            angleVel[2] = 0;
        }

        prevPos.x = x;
        prevPos.y = y;

    }
    // Triggered when releasing the finger and it's not over the object anymore
    public void touchUpMove(int x, int y){
        super.touchUpMove(x, y);
        //    setColor(0.6f,0.7f,1.0f,1.0f);

    }

    public void update(){
        super.update();
        if ( !isPressed() ) {

            setAngleX( angle.getX() + angleVel[2] );
            setAngleY( angle.getY() + angleVel[1] );

            if (angleVel[2]  < 0 ){
                angleVel[2] = angleVel[2] + 0.3f;
                if( angleVel[2] > 0 )
                    angleVel[2] = 0;
            }
            else{
                angleVel[2] = angleVel[2] - 0.3f;
                if( angleVel[2] < 0 )
                    angleVel[2] = 0;
            }

            if (angleVel[1]  < 0 ){
                angleVel[1] = angleVel[1] + 0.3f;
                if( angleVel[1] > 0 )
                    angleVel[1] = 0;
            }
            else{
                angleVel[1] = angleVel[1] - 0.3f;
                if( angleVel[1] < 0 )
                    angleVel[1] = 0;
            }
            //  System.out.println(  angleVel[1] + angleVel[2]  );


        }
    }

}

