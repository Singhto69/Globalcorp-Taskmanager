package com.globalcorp.taskman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.globalcorp.taskman.database.missiondb
import com.globalcorp.taskman.model.mission

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment


        /*if (container != null) {
            val myDataSet = missiondb().loadlist()
            //thisContext = container.getContext()
            //val recyclerView = binding.recyclerView1
            binding.recyclerView1.adapter = MissionAdapter(container.context,myDataSet)
            binding.recyclerView1.setHasFixedSize(true)

        }*/




    }


}