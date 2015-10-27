package com.benpinkas.helloben.leCasseBrique.missions;

import com.benpinkas.helloben.leCasseBrique.Brick;
import com.benpinkas.helloben.leCasseBrique.Mission;

public class MissionTwo extends Mission {

    private Brick b;

    public MissionTwo() {
        b = new Brick(2);
        //FIXME this line is mandatory otherwise nothing works
        bricks.add(b);
    }

    @Override
    public void init() {
        b.setPos(0.5f, 0.4f);
        b.setSize(0.14f);
        b.setCollide(true);
        b.setVisible(true);
        b.setRemainingHp(2);
    }

    @Override
    public boolean completed() {
        return false;
    }
}
