package com.globalcorp.taskman.database

import com.globalcorp.taskman.R
import com.globalcorp.taskman.model.mission

 class missiondb {
    val list = mutableListOf<mission>()

    init {
        list.add(
            mission(
                0,
                "Reinstall Computers",
                "LTU",
                "14152 and c215j,windows",
                "5/8/23",
                "8:30",
                "13:30"
            )
        )
        list.add(
            mission(
                1,
                "Cat daycare",
                "Catcenter , Catstreet 123",
                "Mittens and sr meowsalot. Alina will tell you more",
                "6/8/23",
                "8:30",
                "15:20"
            )
        )
        list.add(
            mission(
                2,
                "Cleaning",
                "Globocorp, globostreet 1",
                "Clean the breakroom and office area," +
                        " it better be mint clean rookie, MINT CLEAN",
                "6/8/23",
                "7:30",
                "16:30"
            )
        )
        list.add(
            mission(
                3,
                "Repair printer",
                "Globocorp, globostreet 2",
                "Room A040203 , Jack informed the colour powder dispenser is faulty",
                "6/8/23",
                "8:00",
                "16:00"
            )
        )
    }

    fun loadlist(): MutableList<mission> {
        return list
    }

}

/* class missiondb {


    fun loadMissions(): List<mission> {
        return listOf<mission>(
            mission(
                0,
                "Reinstall Computers",
                "LTU",
                "14152 and c215j,windows",
                "5/8/23",
                "8:30",
                "13:30"
            ),
            mission(
                1,
                "Cat daycare",
                "Catcenter , Catstreet 123",
                "Mittens and sr meowsalot. Alina will tell you more",
                "6/8/23",
                "8:30",
                "15:20"
            )
        )
    }
}*/