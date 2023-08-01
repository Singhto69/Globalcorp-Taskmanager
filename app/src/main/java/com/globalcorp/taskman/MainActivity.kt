package com.globalcorp.taskman

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // AppBarConfiguration is updated here
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.adminFragment,
            R.id.kittensFragment,
            R.id.missionsFragment   // replace with your actual destination id
            // Add more if you have more top-level destinations
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)




        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                val transparentDrawable = ContextCompat.getDrawable(this, R.drawable.transparent_drawable)
                supportActionBar?.setHomeAsUpIndicator(transparentDrawable) // Hide back button
            } else {
                val upArrow = ContextCompat.getDrawable(this, androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                upArrow?.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
                supportActionBar?.setHomeAsUpIndicator(upArrow)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)  // Show back button
            }
        }

    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }*/



}