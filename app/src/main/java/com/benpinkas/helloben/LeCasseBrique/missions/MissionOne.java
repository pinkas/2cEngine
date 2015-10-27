package com.benpinkas.helloben.leCasseBrique.missions;

import com.benpinkas.helloben.leCasseBrique.Brick;
import com.benpinkas.helloben.leCasseBrique.Mission;

public class MissionOne extends Mission {

    private final float PADDX = 0.05f;
    private final float PADDY = 0.13f;
    private final float SIZEW = 0.14f;
    private final float SIZEH = 0.03f;
    private final int ROW = 4;
    private final int COL = 6;
    private final int totalBricks = ROW * COL;

    public MissionOne() {
        // Bricks of the mission
        brickCount = totalBricks;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Brick b = new Brick(1);
                b.setVisible(false);
                b.setCollide(true);
                bricks.add(b);
            }
        }
        // Enemy
    }

    @Override
    public void init() {
        initBricks();
        initBaddy();
    }

    @Override
    public boolean completed() {
            //For future use
            return false;
    }

    public void initBricks() {
        int index = 0;
        for (int i=0; i < ROW; i++){
            for (int j=0; j < COL; j++){
                bricks.get(index).setPos(PADDX + j * (SIZEW + 0.01f), PADDY + i * (SIZEH + 0.01f));
                bricks.get(index).setSize(SIZEW);
                bricks.get(index).setCollide(true);
                bricks.get(index).setVisible(true);
                bricks.get(index).setRemainingHp(1);
                index++;
            }
        }
    }

    //TODO
    public void initBaddy() {}
}
