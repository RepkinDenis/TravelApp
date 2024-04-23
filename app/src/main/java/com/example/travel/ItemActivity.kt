package com.example.travel

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import androidx.room.Room
import com.example.travel.ui.nav.HomeFragment

class ItemActivity : AppCompatActivity() {
    private var travelId: Int = -1
    private lateinit var db: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val imageView_info: ImageView = findViewById(R.id.imageView_item_info)
        val textView_name_place_info: TextView = findViewById(R.id.textView_name_place_info)
        val textView_price: TextView = findViewById(R.id.textView_price_info)
        val textView_address_info: TextView = findViewById(R.id.textView_address_info)
        val textView_category_info: TextView = findViewById(R.id.textView_category_info)
        val textView_time_info: TextView = findViewById(R.id.textView_time_info)

        db = Room.databaseBuilder(applicationContext, DataBase::class.java, "travel.db")
            .allowMainThreadQueries()
            .build()

        // Загрузка данных о путешествии по travelId и отображение информации в соответствующих элементах макета
        travelId = intent.getIntExtra("travel_id", -1)

        val travel = db.daoTravel().getById(travelId)


        if (travel != null) {
            imageView_info.setImageResource(resources.getIdentifier(travel.image_path, "drawable", packageName))
            textView_name_place_info.text = travel.name_place
            textView_price.text = travel.price.toString()+"p"
            textView_category_info.text = travel.category
            textView_address_info.text = travel.address
            textView_time_info.text = travel.working_hours
        }


        val likeButton = findViewById<ImageButton>(R.id.button_like_icon_info)
        var isLiked = false

        if(travel.liked == 1){
            isLiked = true
        }

        isLiked = !isLiked
        if (!isLiked) {
            likeButton.setImageResource(R.drawable.like_icon_active)
        } else {
            likeButton.setImageResource(R.drawable.like_icon)
        }
    }

    fun onItemButtonClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

    fun onLikeButtonClickLike(view: View) {
        val likeButton = findViewById<ImageButton>(R.id.button_like_icon_info)

        db = Room.databaseBuilder(applicationContext, DataBase::class.java, "travel.db")
            .allowMainThreadQueries()
            .build()
        // Загрузка данных о путешествии по travelId и отображение информации в соответствующих элементах макета
        travelId = intent.getIntExtra("travel_id", -1)

        val travel = db.daoTravel().getById(travelId)

        var isLiked = false

        if(travel.liked == 1){
            isLiked = true
        }

        likeButton.setOnClickListener {
            db = DataBase.getInstance(this)
            val travelDao = db.daoTravel()
            isLiked = !isLiked
            if (isLiked) {
                likeButton.setImageResource(R.drawable.like_icon_active)
                travelDao.updateLike(travelId)
            } else {
                likeButton.setImageResource(R.drawable.like_icon)
                travelDao.updateDisLike(travelId)
            }
        }
    }
}