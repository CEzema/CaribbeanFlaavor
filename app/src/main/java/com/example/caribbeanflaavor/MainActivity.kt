package com.example.caribbeanflaavor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.nav_host_fragment)
        toolbar.setupWithNavController(navController)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart_button-> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_cart)
            R.id.home_item -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_home2)
            R.id.menu_item -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_menu)
            R.id.favorite_item -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_favorite)
        }
        return super.onOptionsItemSelected(item)
    }
}
