package com.example.travel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat

class CityActivityLike : AppCompatActivity() {
    private lateinit var db: DataBase
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_like)

        val searchView: SearchView = findViewById(R.id.searchViewCity_like)

        searchView.setOnClickListener {
            searchView.isIconified = false
            searchView.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // логика для выполнения поиска
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // логика для реагирования на изменения текста в SearchView
                return true
            }
        })

        val textViewCity = findViewById<TextView>(R.id.textView_city_like)
        val drawable = ContextCompat.getDrawable(this, R.drawable.map)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        textViewCity.setCompoundDrawables(drawable, null, null, null)
        textViewCity.compoundDrawablePadding = 16

        db = DataBase.getInstance(this)
        val daoTravel = db.daoTravel()
        val cities: List<City> = daoTravel.getAllCity()
        var adapterTravel = TravelAdapter(this, cities, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@CityActivityLike, LikeActivity::class.java)
                intent.putExtra("selectedPosition", position)
                startActivity(intent)
            }
        })


        val listView: ListView = findViewById(R.id.listCity_like)
        listView.adapter = adapterTravel
    }

    class TravelAdapter(context: Context, private val travelsList: List<City>, private val onItemClickListener: OnItemClickListener) : BaseAdapter() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getCount(): Int {
            return travelsList.size
        }

        override fun getItem(position: Int): Any {
            return travelsList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if (view == null) {
                view = inflater.inflate(R.layout.list_item_travel, parent, false)
            }

            val city = getItem(position) as City

            view?.findViewById<TextView>(R.id.textViewTitle)?.text = city.name_city

            view?.setOnClickListener {
                onItemClickListener.onItemClick(position + 1)
            }


            return view!!
        }
    }

    fun onCityButtonClickBack_like(view: View) {
        val intent = Intent(this, LikeActivity::class.java)

        startActivity(intent)
    }
}