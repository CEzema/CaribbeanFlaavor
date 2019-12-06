package com.example.caribbeanflaavor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView




class MainActivity : AppCompatActivity() {

    var smsCountTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.nav_host_fragment)
        toolbar.setupWithNavController(navController)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        //smsCountTxt = actionView.findViewById(R.id.notification_badge)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping_cart_button -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_cart)
            R.id.home_item -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_home2)
            R.id.menu_item -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_menu)
            R.id.favorite_item -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_favorite)
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun setupBadge(){
//        if(smsCountTxt != null){
//
//        }
//        else{
//            smsCountTxt = String(Math.min(0,99))
//            if (smsCountTxt.)
//        }
//    }
}
