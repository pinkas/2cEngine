package com.benpinkas.helloben.leCasseBrique;

import java.util.ArrayList;
import java.util.List;

public abstract class Mission {

    protected int brickCount;

    protected List<Brick> bricks = new ArrayList<Brick>();

    public abstract void init();

    public abstract boolean completed();

    public final int addBricksToScene(GameScene s){
        for (Brick obj : bricks) {
            s.add(obj);
            s.getTheBall().collisionService.addCollider(obj);
        }
        return bricks.size();
    }
}
