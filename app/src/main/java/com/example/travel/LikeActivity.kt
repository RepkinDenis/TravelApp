package com.example.travel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.travel.R
import com.example.travel.databinding.ActivityMainBinding
import com.example.travel.ui.nav.HomeFragment
import com.example.travel.ui.nav.LikeFragment

class LikeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_like)

        val selectedPosition = intent?.getIntExtra("selectedPosition", 0) ?: 0

        val bundle = Bundle()

        bundle.putInt("selectedPosition", selectedPosition)

        val fragment = LikeFragment()
        fragment.arguments = bundle

        loadFragment(LikeFragment())
    }

    private  fun loadFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}