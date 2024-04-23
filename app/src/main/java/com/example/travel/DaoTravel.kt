package com.example.travel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
public interface DaoTravel {

    @Query("SELECT * FROM travel")
    fun getAll(): List<Travel>

    @Query("SELECT * FROM travel WHERE id = :id")
    fun getById(id: Int): Travel

    @Query("SELECT * FROM city WHERE id = :id")
    fun getCityById(id: Int): City

    @Query("SELECT * FROM travel WHERE liked = 1")
    fun getAllLiked(): List<Travel>

    @Query("SELECT * FROM travel WHERE city_id = :id")
    fun getAllByCity(id: Int): List<Travel>


    //сортировка
    @Query("SELECT * FROM travel ORDER BY name_place ASC")
    fun getSotrAZ(): List<Travel>

    @Query("SELECT * FROM travel ORDER BY name_place DESC")
    fun getSortZA(): List<Travel>

    @Query("SELECT * FROM travel ORDER BY price ASC")
    fun getSotrPticeAsc(): List<Travel>

    @Query("SELECT * FROM travel ORDER BY price DESC")
    fun getSotrPticeDesc(): List<Travel>

    @Query("SELECT * FROM travel WHERE price = 0")
    fun getFree(): List<Travel>

    //сортировка город
    @Query("SELECT * FROM travel WHERE city_id = :id")
    fun getAll(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE city_id = :id ORDER BY name_place ASC")
    fun getSotrAZ(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE city_id = :id ORDER BY name_place DESC")
    fun getSortZA(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE city_id = :id ORDER BY price ASC")
    fun getSotrPticeAsc(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE city_id = :id ORDER BY price DESC")
    fun getSotrPticeDesc(id: Int): List<Travel>

    @Query("SELECT * FROM travel  WHERE price = 0 AND city_id = :id")
    fun getFree(id: Int): List<Travel>

    //сортировка избраное
    @Query("SELECT * FROM travel WHERE liked = 1 ORDER BY name_place ASC")
    fun getSotrAZLike(): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 ORDER BY name_place DESC")
    fun getSortZALike(): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 ORDER BY price ASC")
    fun getSotrPticeAscLike(): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 ORDER BY price DESC")
    fun getSotrPticeDescLike(): List<Travel>

    @Query("SELECT * FROM travel WHERE price = 0 AND liked = 1")
    fun getFreeLike(): List<Travel>

    //сортировка избраное город
    @Query("SELECT * FROM travel WHERE liked = 1 AND city_id = :id")
    fun getAllLike(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 AND city_id = :id ORDER BY name_place ASC")
    fun getSotrAZLike(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 AND city_id = :id ORDER BY name_place DESC")
    fun getSortZALike(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 AND city_id = :id ORDER BY price ASC")
    fun getSotrPticeAscLike(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE liked = 1 AND city_id = :id ORDER BY price DESC")
    fun getSotrPticeDescLike(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE price = 0 AND liked = 1 AND city_id = :id")
    fun getFreeLike(id: Int): List<Travel>


    //категории
    @Query("SELECT * FROM travel WHERE category = 'Кафе'")
    fun getCafe(): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Музей'")
    fun getMuseum(): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Парк'")
    fun getPark(): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Знаковое место'")
    fun getIconicPlace(): List<Travel>

    //категории город
    @Query("SELECT * FROM travel WHERE category = 'Кафе' AND city_id = :id")
    fun getCafe(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Музей' AND city_id = :id")
    fun getMuseum(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Парк' AND city_id = :id")
    fun getPark(id: Int): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Знаковое место' AND city_id = :id")
    fun getIconicPlace(id: Int): List<Travel>

    //категории избраное
    @Query("SELECT * FROM travel WHERE category = 'Кафе' AND liked = 1")
    fun getCafeLike(): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Музей' AND liked = 1")
    fun getMuseumLike(): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Парк' AND liked = 1")
    fun getParkLike(): List<Travel>

    @Query("SELECT * FROM travel WHERE category = 'Знаковое место' AND liked = 1")
    fun getIconicPlaceLike(): List<Travel>

    //категории избраное город
    @Query("SELECT * FROM travel JOIN city ON travel.city_id = city.id WHERE city.name_city = :searchQuery AND liked = 1")
    fun getAllLiked(searchQuery: String): List<Travel>

    @Query("SELECT * FROM travel JOIN city ON travel.city_id = city.id WHERE category = 'Кафе' AND liked = 1 AND city.name_city = :searchQuery")
    fun getCafeLike(searchQuery: String): List<Travel>

    @Query("SELECT * FROM travel JOIN city ON travel.city_id = city.id WHERE category = 'Музей' AND liked = 1 AND city.name_city = :searchQuery")
    fun getMuseumLike(searchQuery: String): List<Travel>

    @Query("SELECT * FROM travel JOIN city ON travel.city_id = city.id WHERE category = 'Парк' AND liked = 1 AND city.name_city = :searchQuery")
    fun getParkLike(searchQuery: String): List<Travel>

    @Query("SELECT * FROM travel JOIN city ON travel.city_id = city.id WHERE category = 'Знаковое место' AND liked = 1 AND city.name_city = :searchQuery")
    fun getIconicPlaceLike(searchQuery: String): List<Travel>

    //поиск
    @Query("SELECT * FROM travel WHERE name_place LIKE :searchQuery")
    fun searchTravelsByNamePlace(searchQuery: String): List<Travel>

    //поиск избраное
    @Query("SELECT * FROM travel WHERE name_place LIKE :searchQuery AND liked = 1")
    fun searchTravelsByNamePlaceLike(searchQuery: String): List<Travel>

    //поиск город
    @Query("SELECT * FROM CITY WHERE name_city LIKE :searchQuery")
    fun searchTravelsByCity(searchQuery: String): List<City>

    //город избраное
    @Query("SELECT DISTINCT city.name_city FROM travel INNER JOIN city ON travel.city_id = city.id WHERE liked = 1")
    fun CityLikeSpinner(): List<String>

    //избраное по 1 городу
    @Query("SELECT travel.* FROM travel JOIN city ON travel.city_id = city.id WHERE city.name_city = :searchQuery AND liked = 1")
    fun searchTravelsByCityLike(searchQuery: String): List<Travel>

    @Insert
    fun insert(travel: Travel)

    @Update
    fun update(travel: Travel)

    @Delete
    fun delete(travel: Travel)

    @Query("UPDATE travel SET liked=0 WHERE id = :id")
    fun updateDisLike(id: Int)

    @Query("UPDATE travel SET liked=1 WHERE id = :id")
    fun updateLike(id: Int)

    //поиск id по адресу
    @Query("SELECT id FROM travel WHERE address = :searchQuery")
    fun selectIdbyName(searchQuery: String): Int

    //city
    @Query("SELECT * FROM city")
    fun getAllCity(): List<City>

    @Query("SELECT * FROM city WHERE id = :id")
    fun getByIdCity(id: Int): City
}