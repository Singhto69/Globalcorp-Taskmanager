package com.globalcorp.taskman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        //val navController2 = findNavController(R.id.nav_host_fragment)

        val navController2 = navHostFragment.navController
        //setSupportActionBar(findViewById(R.id.maintoolbar))
        val appBarConfig = AppBarConfiguration(navController2.graph)
        findViewById<Toolbar>(R.id.maintoolbar).setupWithNavController(navController2, appBarConfig)


        /*val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/

        /*if (container != null) {
            val myDataSet = missiondb().loadlist()
            //thisContext = container.getContext()
            //val recyclerView = binding.recyclerView1
            binding.recyclerView1.adapter = MissionAdapter(container.context,myDataSet)
            binding.recyclerView1.setHasFixedSize(true)

        }*/


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        val layoutButton = menu?.findItem(R.id.action_switch_layout)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}

/*
  <style name="Theme.GlobalcorpTaskmanager" parent="Theme.MaterialComponents.DayNight.DarkActionBar">

Theme.AppCompat.Light.NoActionBar"

android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar" <-- widget.toolbar

in activity main
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/maintoolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:elevation="4dp"
        android:theme="@style/ThemeOverlay.AppCompat.ActionBar"


        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"


        />

 */