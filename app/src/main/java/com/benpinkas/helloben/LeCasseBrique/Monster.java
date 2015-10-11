package com.benpinkas.helloben.leCasseBrique;

import android.graphics.PointF;

import com.benpinkas.bEngine.object.Bobject;
import com.benpinkas.bEngine.scene.Scene;

public class Monster extends Bobject {

    public BricksGroup leftHand;
    public BricksGroup rightHand;
    public BricksGroup face;

    public Monster(Scene scene){

        super(0,0,0,0);

        float margin = 0.005f;
        float size_w = 0.03f;
        float size_h = 0.03f;
        PointF[] relativePosRightHand = new PointF[4];
        relativePosRightHand[0] = new PointF(0,0);
        relativePosRightHand[1] = new PointF(0, size_h + margin);
        relativePosRightHand[2] = new PointF(0, (size_h + margin)*2);
        relativePosRightHand[3] = new PointF(size_w + margin, 0);

        PointF[] relativePosLeftHand = new PointF[4];
        relativePosLeftHand[0] = new PointF(0, 0);
        relativePosLeftHand[1] = new PointF(size_w+margin, 0);
        relativePosLeftHand[2] = new PointF(size_w+margin, size_h + margin);
        relativePosLeftHand[3] = new PointF(size_w+margin, (size_h + margin)*2);

        PointF[] relativePosFace = new PointF[4];
        relativePosFace[0] = new PointF(0,0);
        relativePosFace[1] = new PointF(size_w*5, 0);
        relativePosFace[2] = new PointF(size_w, size_w*3);
        relativePosFace[3] = new PointF(size_w*2.7f, size_w*1.5f);

        PointF[] sizeFace = new PointF[4];
        sizeFace[0] = new PointF(size_w, size_h);
        sizeFace[1] = new PointF(size_w, size_h);
        sizeFace[2] = new PointF(size_w*4, size_h);
        sizeFace[3] = new PointF(size_w/2, size_h/2);


        PointF[] sizeRight = new PointF[4];
        sizeRight[0] = new PointF(size_w, size_w);
        sizeRight[1] = new PointF(size_w, size_w);
        sizeRight[2] = new PointF(size_w, size_w);
        sizeRight[3] = new PointF(size_w, size_h * 3 + 2 * margin);

        PointF[] sizeLeft = new PointF[4];
        sizeLeft[0] = new PointF(size_w, size_h *3 + 2 * margin);
        sizeLeft[1] = new PointF(size_w, size_w);
        sizeLeft[2] = new PointF(size_w, size_w);
        sizeLeft[3] = new PointF(size_w, size_w);

        leftHand = new BricksGroup( 0.3f, 0.3f, relativePosLeftHand, sizeLeft );
        rightHand = new BricksGroup( 0.7f, 0.3f, relativePosRightHand, sizeRight);
        face = new BricksGroup(0.33f, 0.2f, relativePosFace, sizeFace);

        leftHand.setCollide(true);
        rightHand.setCollide(true);
        face.setCollide(true);

        scene.add(leftHand);
        scene.add(rightHand);
        scene.add(face);
        face.setPos(0.44f, 0.2f);

        rightHand.crushPlayer.setACTION_T(1500);
        rightHand.crushPlayer.setOtherAction(leftHand.crushPlayer);
        scene.add(this);
    }

    public void setVisible(boolean visible){
        leftHand.setVisible(visible);
        rightHand.setVisible(visible);
        face.setVisible(visible);
    }

    @Override
    public void setPos(float x, float y){

    }

    @Override
    public void update(float dt){
       if(!leftHand.crushPlayer.isInProgress())
           rightHand.crushPlayer.incCpt();
    }

}
