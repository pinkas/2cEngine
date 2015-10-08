package com.benpinkas.bEngine.object;

import android.graphics.PointF;
import java.util.ArrayList;


public class BcontainerObject extends Bobject {

    private ArrayList<BglObject> child = new ArrayList<BglObject>();
    private ArrayList<Float> childPos = new ArrayList<Float>();

    public BcontainerObject(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void addChild( BglObject o, float offX, float offY) {
        child.add(o);
        childPos.add(offX);
        childPos.add(offY);
    }

    public ArrayList<BglObject> getChild() {
        return child;
    }

    public void setCollide(boolean collide) {
        for ( BglObject o : child ) {
            o.setCollide(collide);
        }
    }

    @Override
    public float getPosX() {
        return super.getPosX();
    }

    @Override
    public float getPosX(float centerPointX) {
        return super.getPosX(centerPointX);
    }

    @Override
    public float getPosY() {
        return super.getPosY();
    }

    @Override
    public float getPosY(float centerPointY) {
        return super.getPosY(centerPointY);
    }

    private void moveChild(){
        float contX = getPosX();
        float contY = getPosY();

        int i = 0;
        for ( BglObject o : child ) {
            o.setPos( contX + childPos.get(i), contY + childPos.get(i+1) );
            i = i + 2;
        }
    }

    @Override
    public void setPos(PointF pos) {
        super.setPos(pos);
        moveChild();
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        moveChild();
    }

    @Override
    public void setPos(Bobject o) {
        super.setPos(o);
        moveChild();
    }

    @Override
    public void setPos(float x, float y, float oriX, float oriY) {
        super.setPos(x, y, oriX, oriY);
        moveChild();
    }

    public void setVisible(boolean visible){
        for ( BglObject o : child ) {
            o.setVisible(visible);
        }
    }
}
