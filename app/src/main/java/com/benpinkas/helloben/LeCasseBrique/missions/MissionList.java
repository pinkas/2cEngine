package com.benpinkas.helloben.leCasseBrique.missions;

import com.benpinkas.helloben.leCasseBrique.Mission;

public class MissionList {
    private static Mission missions[] = new Mission[3];
    private static final MissionList missionList = new MissionList();

    private MissionList() {
        missions[0] = new MissionOne();
        missions[1] = new MissionTwo();
        missions[2] = new MissionOne();
    }

    public static Mission[] getMissions(){
        return missions;
    }
}
