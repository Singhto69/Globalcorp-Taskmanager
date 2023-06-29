package com.globalcorp.taskman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.findNavController
class MainActivity : AppCompatActivity(R.layout.activity_main) {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    }

}


/* Setup the toolbar using the navcontroller , this simplifies the process of making
  the title fragment specific
   */
/*val toolbar = findViewById<Toolbar>(R.id.maintoolbar)
setSupportActionBar(toolbar)
val navController2 = navHostFragment.navController
val appBarConfig = AppBarConfiguration(navController2.graph)
toolbar.setupWithNavController(navController2, appBarConfig)*/

//findViewById<Toolbar>(R.id.maintoolbar).setupWithNavController(navController2, appBarConfig)

//val toolbar = findViewById<Toolbar>(R.id.maintoolbar)
//setSupportActionBar(toolbar)

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

/*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    //val layoutButton = menu?.findItem(R.id.action_switch_layout)

    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return super.onOptionsItemSelected(item)
}

fun onFragment1ButtonClick(view: View) {

    Toast.makeText(this, "Fragment 1 button clicked", Toast.LENGTH_SHORT).show()

}*/