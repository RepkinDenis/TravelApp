package com.example.travel

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.NavController
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.travel.databinding.ActivityMainBinding
import com.example.travel.ui.nav.HomeFragment
import com.example.travel.ui.nav.InfoFragment
import com.example.travel.ui.nav.LikeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController
    private var travelId: Int = -1
    lateinit var db: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_like, R.id.navigation_info
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)



        Room.databaseBuilder(this, DataBase::class.java, "travel.db")
            .createFromAsset("travel.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        val selectedPosition = intent?.getIntExtra("selectedPosition", 0) ?: 0

        val bundle = Bundle()

        bundle.putInt("selectedPosition", selectedPosition)

        val fragment = HomeFragment()
        fragment.arguments = bundle

        loadFragment(fragment)

        navView.setOnNavigationItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    switchIcon(item.itemId, R.drawable.home_active)
                    true
                }
                R.id.navigation_like -> {
                    loadFragment(LikeFragment())
                    switchIcon(item.itemId, R.drawable.like_active)
                    true
                }
                R.id.navigation_info -> {
                    loadFragment(InfoFragment())
                    switchIcon(item.itemId, R.drawable.info_active)
                    true
                }
                else -> false
            }

            handled
        }

    }


    private  fun loadFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    private fun switchIcon(itemId: Int, iconId: Int) {
        val menu = navView.menu
        for (index in 0 until menu.size()) {
            val menuItem = menu.getItem(index)
            if (menuItem.itemId == itemId) {
                menuItem.setIcon(iconId)
            } else {
                when (menuItem.itemId) {
                    R.id.navigation_home -> menuItem.setIcon(R.drawable.home)
                    R.id.navigation_like -> menuItem.setIcon(R.drawable.like)
                    R.id.navigation_info -> menuItem.setIcon(R.drawable.info)
                }
            }
        }
    }
}