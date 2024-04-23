package com.example.travel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Travel::class, City::class], version = 5)
abstract class DataBase : RoomDatabase() {
    abstract fun daoTravel(): DaoTravel
    companion object {
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            if (INSTANCE == null) {
                synchronized(DataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DataBase::class.java, "travel.db")
                        .createFromAsset("travel.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as DataBase
        }
    }
}


