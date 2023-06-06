package com.globalcorp.taskman.database

import com.globalcorp.taskman.models.Mission

 class missiondbhardcoded {
    val list = mutableListOf<Mission>()

    init {
        list.add(
            Mission(
                0,
                "Reinstall Computers",
                "LTU",
                "14152 and c215j,windows",
                "5/8/23",
                "8:30",
                "13:30",
                1
            )
        )
        list.add(
            Mission(
                1,
                "Cat daycare",
                "Catcenter , Catstreet 123",
                "Mittens and sr meowsalot. Alina will tell you more",
                "6/8/23",
                "8:30",
                "15:20",
                4
            )
        )
        list.add(
            Mission(
                2,
                "Cleaning",
                "Globocorp, globostreet 1",
                "Clean the breakroom and office area," +
                        " it better be mint clean rookie, MINT CLEAN",
                "6/8/23",
                "7:30",
                "16:30",
                1
            )
        )
        list.add(
            Mission(
                3,
                "Repair printer",
                "Globocorp, globostreet 2",
                "Room A040203 , Jack informed the colour powder dispenser is faulty",
                "6/8/23",
                "8:00",
                "16:00"
                ,4
            )
        )
    }

    fun loadlist(): MutableList<Mission> {
        return list
    }

}