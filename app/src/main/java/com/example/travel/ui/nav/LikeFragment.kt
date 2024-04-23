package com.example.travel.ui.nav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.travel.City
import com.example.travel.CityActivity
import com.example.travel.CityActivityLike
import com.example.travel.DataBase
import com.example.travel.ItemActivity
import com.example.travel.ItemDecoration
import com.example.travel.R
import com.example.travel.Travel
import com.example.travel.databinding.FragmentDashboardBinding
import com.example.travel.ui.dashboard.DashboardViewModel

class LikeFragment: Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var buttonCity: Button

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = Room.databaseBuilder(requireContext(), DataBase::class.java, "travel.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        val daoTravel = db.daoTravel()
        var travels: List<Travel> = daoTravel.getAllLiked()
        var adapterTravel = TravelAdapter(requireContext(), travels)

        var recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapterTravel
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        val spaceInPixels = 16
        recyclerView.addItemDecoration(ItemDecoration(spaceInPixels))

        val spinner: Spinner = binding.sortSpinner
        val sortOptions = arrayOf("По умолчанию", "По афавиту от А до Я", "По афавиту от Я до А","По возрастанию цены","По убыванию цены","Бесплатные места")
        val iconSpinner = intArrayOf(
            R.drawable.category_def, R.drawable.category_az, R.drawable.category_za,
            R.drawable.category, R.drawable.category_rev,
            R.drawable.category_rub)
        val adapter = CustomAdapter(requireContext(), sortOptions, iconSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        var CityList: List<String> = daoTravel.CityLikeSpinner()
        CityList = listOf("Город") + CityList
        val spinnerCity: Spinner = binding.citySpinner
        val spinnerData = CityList.toTypedArray()

        val iconSpinnerCity = IntArray(spinnerData.size) { R.drawable.map } // массив значков-по умолчанию
        val adapterCity = CustomAdapter(requireContext(), spinnerData, iconSpinnerCity)
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapterCity


        val args = arguments
        val selectedPosition = args?.getInt("selectedPosition") ?: 0
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val daoTravel = db.daoTravel()
                var travels: List<Travel> = daoTravel.getAllLiked()
                if (sortOptions[position] == "По умолчанию"){
                    travels = daoTravel.getAllLiked()
                    if(selectedPosition != 0){
                        travels = daoTravel.getAllLike(selectedPosition)
                    }
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }else if (sortOptions[position] == "По афавиту от А до Я"){
                    travels = daoTravel.getSotrAZLike()
                    if(selectedPosition != 0){
                        travels = daoTravel.getSotrAZLike(selectedPosition)
                    }
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }else if (sortOptions[position] == "По афавиту от Я до А"){
                    travels = daoTravel.getSortZALike()
                    if(selectedPosition != 0){
                        travels = daoTravel.getSortZALike(selectedPosition)
                    }
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }else if (sortOptions[position] == "По возрастанию цены"){
                    travels = daoTravel.getSotrPticeAscLike()
                    if(selectedPosition != 0){
                        travels = daoTravel.getSotrPticeAscLike(selectedPosition)
                    }
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }else if (sortOptions[position] == "По убыванию цены"){
                    travels = daoTravel.getSotrPticeDescLike()
                    if(selectedPosition != 0){
                        travels = daoTravel.getSotrPticeDescLike(selectedPosition)
                    }
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }else if (sortOptions[position] == "Бесплатные места"){
                    travels = daoTravel.getFreeLike()
                    if(selectedPosition != 0){
                        travels = daoTravel.getFreeLike(selectedPosition)
                    }
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия при отсутствии выбора
            }
        }

        var selectedItem: String ?= null
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){
                    selectedItem = spinnerData[position]
                    val daoTravel = db.daoTravel()
                    var travels: List<Travel> = daoTravel.searchTravelsByCityLike(spinnerData[position])
                    val adapterTravel = TravelAdapter(requireContext(), travels)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действия при отсутствии выбора
            }
        }


        button1 = root.findViewById<Button>(R.id.button)
        button2 = root.findViewById<Button>(R.id.button10)
        button3 = root.findViewById<Button>(R.id.button11)
        button4 = root.findViewById<Button>(R.id.button12)
        button5 = root.findViewById<Button>(R.id.button13)

        button1.setOnClickListener {
            setButtonStyle(button1, true)
            setButtonStyle(button2, false)
            setButtonStyle(button3, false)
            setButtonStyle(button4, false)
            setButtonStyle(button5, false)

            val selectedItemCopy = selectedItem
            val daoTravel = db.daoTravel()
            var travels: List<Travel> = daoTravel.getAllLiked()
            if(selectedItemCopy != null){
                travels = daoTravel.getAllLiked(selectedItemCopy)
            }
            val adapterTravel = TravelAdapter(requireContext(), travels)
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = adapterTravel
            var MessageText = root.findViewById<TextView>(R.id.textView_Null)
            MessageText.text = ""
            if (travels.size == 0){
                MessageText = root.findViewById<TextView>(R.id.textView_Null)
                MessageText.text = "Мест в данной категории не найдено"
            }
        }

        button2.setOnClickListener {
            setButtonStyle(button1, false)
            setButtonStyle(button2, true)
            setButtonStyle(button3, false)
            setButtonStyle(button4, false)
            setButtonStyle(button5, false)

            val selectedItemCopy = selectedItem
            val daoTravel = db.daoTravel()
            travels = daoTravel.getCafeLike()
            if(selectedItemCopy != null){
                travels = daoTravel.getCafeLike(selectedItemCopy)
            }
            val adapterTravel = TravelAdapter(requireContext(), travels)
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = adapterTravel
            var MessageText = root.findViewById<TextView>(R.id.textView_Null)
            MessageText.text = ""
            if (travels.size == 0){
                MessageText = root.findViewById<TextView>(R.id.textView_Null)
                MessageText.text = "Мест в данной категории не найдено"
            }
        }
        button3.setOnClickListener {
            setButtonStyle(button1, false)
            setButtonStyle(button2, false)
            setButtonStyle(button3, true)
            setButtonStyle(button4, false)
            setButtonStyle(button5, false)

            val selectedItemCopy = selectedItem
            val daoTravel = db.daoTravel()
            travels = daoTravel.getMuseumLike()
            if(selectedItemCopy != null){
                travels = daoTravel.getMuseumLike(selectedItemCopy)
            }
            val adapterTravel = TravelAdapter(requireContext(), travels)
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = adapterTravel
            var MessageText = root.findViewById<TextView>(R.id.textView_Null)
            MessageText.text = ""
            if (travels.size == 0){
                MessageText = root.findViewById<TextView>(R.id.textView_Null)
                MessageText.text = "Мест в данной категории не найдено"
            }
        }
        button4.setOnClickListener {
            setButtonStyle(button1, false)
            setButtonStyle(button2, false)
            setButtonStyle(button3, false)
            setButtonStyle(button4, true)
            setButtonStyle(button5, false)

            val selectedItemCopy = selectedItem
            val daoTravel = db.daoTravel()
            travels = daoTravel.getParkLike()
            if(selectedItemCopy != null){
                travels = daoTravel.getParkLike(selectedItemCopy)
            }
            val adapterTravel = TravelAdapter(requireContext(), travels)
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = adapterTravel
            var MessageText = root.findViewById<TextView>(R.id.textView_Null)
            MessageText.text = ""
            if (travels.size == 0){
                MessageText = root.findViewById<TextView>(R.id.textView_Null)
                MessageText.text = "Мест в данной категории не найдено"
            }
        }
        button5.setOnClickListener {
            setButtonStyle(button1, false)
            setButtonStyle(button2, false)
            setButtonStyle(button3, false)
            setButtonStyle(button4, false)
            setButtonStyle(button5, true)

            val selectedItemCopy = selectedItem
            val daoTravel = db.daoTravel()
            travels = daoTravel.getIconicPlaceLike()
            if(selectedItemCopy != null){
                travels = daoTravel.getIconicPlaceLike(selectedItemCopy)
            }
            val adapterTravel = TravelAdapter(requireContext(), travels)
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = adapterTravel

            var MessageText = root.findViewById<TextView>(R.id.textView_Null)
            MessageText.text = ""
            if (travels.size == 0){
                MessageText = root.findViewById<TextView>(R.id.textView_Null)
                MessageText.text = "Мест в данной категории не найдено"
            }
        }


        travels = daoTravel.getAllByCity(selectedPosition)
        adapterTravel = TravelAdapter(requireContext(), travels)
        recyclerView.adapter = adapterTravel

        val searchView: SearchView = root.findViewById(R.id.searchView)

        searchView.setOnClickListener {
            searchView.isIconified = false
            searchView.requestFocus()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT) // Показ клавиатуры
        }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // логика обновления результата поиска
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // логика по выполнению поиска
                if (query != null) {
                    val searchQuery = "%$query%"
                    val dao = db.daoTravel()
                    val searchResults = dao.searchTravelsByNamePlaceLike(searchQuery)
                    val adapterTravel = TravelAdapter(requireContext(), searchResults)
                    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.adapter = adapterTravel

                    var MessageText = root.findViewById<TextView>(R.id.textView_Null)
                    MessageText.text = ""
                    if (searchResults.size == 0){
                        val MessageText = root.findViewById<TextView>(R.id.textView_Null)
                        MessageText.text = "Мест с данным названием не найдено"
                    }

                    val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    val currentFocus = requireActivity().currentFocus
                    if (currentFocus != null) {
                        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                    }

                }
                return true
            }
        })


        return root
    }

    class TravelAdapter(private val context: Context, private val travelsList: List<Travel>) : RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

        inner class TravelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView_item)
            val textView_name_place: TextView = itemView.findViewById(R.id.textView_name_place)
            val textView_address: TextView = itemView.findViewById(R.id.textView_address)
            val likeButton: ImageButton = itemView.findViewById(R.id.button_like_icon)
            val db = Room.databaseBuilder(context, DataBase::class.java, "travel.db")
                .allowMainThreadQueries()
                .build()
            init {
                imageView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val travel = travelsList[position]
                        val intent = Intent(context, ItemActivity::class.java)
                        intent.putExtra("travel_id", travel.id)
                        context.startActivity(intent)
                    }
                }
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel, parent, false)
            return TravelViewHolder(view)
        }

        override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
            val travel = travelsList[position]
            val travelId = holder.db.daoTravel().selectIdbyName(travel.address)

            holder.likeButton.setOnClickListener {
                if (travel.liked == 0) {
                    holder.likeButton.setImageResource(R.drawable.like_icon_active) // Установите иконку для состояния "liked"
                    holder.db.daoTravel().updateLike(travelId)
                } else {
                    holder.likeButton.setImageResource(R.drawable.like_icon) // Установите иконку для состояния "not liked"
                    holder.db.daoTravel().updateDisLike(travelId)
                }
            }

            if (travel.liked == 1) {
                val drawableId_icon = context.resources.getIdentifier("like_icon_active", "drawable", context.packageName)
                holder.likeButton.setImageResource(drawableId_icon)
            } else {
                val drawableId_icon = context.resources.getIdentifier("like_icon", "drawable", context.packageName)
                holder.likeButton.setImageResource(drawableId_icon)
            }

            val imagePath = travel.image_path
            if (imagePath != null) {
                val drawableId = context.resources.getIdentifier(imagePath, "drawable", context.packageName)
                holder.imageView.setImageResource(drawableId)
            }
            holder.textView_name_place.text = travel.category +": '"+ travel.name_place +"'"
            holder.textView_address.text = travel.address
        }

        override fun getItemCount(): Int {
            return travelsList.size
        }
    }


    //Spinner
    class CustomAdapter(context: Context, private val sortOptions: Array<String>, private val iconSpinner: IntArray) :
        ArrayAdapter<String>(context, R.layout.custom_spinner_item, sortOptions)  {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val row = inflater.inflate(R.layout.custom_spinner_item, parent, false)

            val textView = row.findViewById<TextView>(R.id.text_view)
            val imageView = row.findViewById<ImageView>(R.id.image_view)

            textView.text = sortOptions[position]
            imageView.setImageResource(iconSpinner[position])

            return row
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getView(position, convertView, parent)
        }
    }

    fun setButtonStyle(button: Button, isActive: Boolean) {
        val background = if (isActive) R.drawable.custom_button_background_active else R.drawable.custom_button_background
        val textColor = if (isActive) R.color.app_color else R.color.greytext

        button.background = ContextCompat.getDrawable(requireContext(), background)
        button.setTextColor(ContextCompat.getColor(requireContext(), textColor))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}