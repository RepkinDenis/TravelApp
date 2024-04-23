package com.example.travel

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.sql.SQLException


//class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, SCHEMA) {
//
//    companion object {
//        private val DB_NAME = "travel.db" // название бд
//        private val SCHEMA = 1 // версия базы данных
//        val TABLE = "travel" // название таблицы в бд
//        // названия столбцов
//        val COLUMN_id = "id"
//        val COLUMN_name_place = "name_place"
//        val COLUMN_category = "category"
//        val COLUMN_address = "address"
//        val COLUMN_price = "price"
//        val COLUMN_working_hours = "working_hours"
//        val COLUMN_city = "city"
//        private var DB_PATH: String? = null
//    }
//
//
//    init {
//        if (context != null) {
//            DB_PATH = context.filesDir.path + DB_NAME
//        }
//    }
//
//}

internal class DatabaseHelper(private val myContext: Context) :
    SQLiteOpenHelper(myContext, DB_NAME, null, SCHEMA) {
    init {
        //DB_PATH = myContext.filesDir.path +"/"+ DB_NAME
        DB_PATH = "/data/data/com.example.travel/databases/travel.db"
    }
    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun create_db()  {
        val file = File(DB_PATH)
        if (!file.exists()) {
            // получаем локальную бд как поток
            myContext.assets.open(DB_NAME).use { myInput ->
                // Открываем пустую бд
                FileOutputStream(DB_PATH).use { myOutput ->
                    // побайтово копируем данные
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (myInput.read(buffer).also { length = it } > 0) {
                        myOutput.write(buffer, 0, length)
                    }
                    myOutput.flush()
                }
            }
        }

//        //Открываем локальную БД как входящий поток
//        val myInput: InputStream = myContext.getAssets().open("travel.db")
//
//        //Путь ко вновь созданной БД
//        val outFileName = DB_PATH
//
//        //Открываем пустую базу данных как исходящий поток
//        val myOutput: OutputStream = FileOutputStream(outFileName)
//
//        //перемещаем байты из входящего файла в исходящий
//        val buffer = ByteArray(1024)
//        var length: Int
//        while (myInput.read(buffer).also { length = it } > 0) {
//            myOutput.write(buffer, 0, length)
//        }
//
//        //закрываем потоки
//        myOutput.flush()
//        myOutput.close()
//        myInput.close()
    }

    @Throws(SQLException::class)
    fun open(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(DB_PATH!!, null, SQLiteDatabase.OPEN_READWRITE)
    }

    companion object {
        private val DB_NAME = "database/travel.db" // название бд
        private val SCHEMA = 1 // версия базы данных
        val TABLE = "travel" // название таблицы в бд
        // названия столбцов
        val COLUMN_id = "id"
        val COLUMN_name_place = "name_place"
        val COLUMN_category = "category"
        val COLUMN_address = "address"
        val COLUMN_price = "price"
        val COLUMN_working_hours = "working_hours"
        val COLUMN_city = "city"
        private var DB_PATH: String? = null
    }
}


//    fun addTravelRecord(name: String, category: String, address: String, price: Int, workingHours: String, city: String) {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put("name_place", name)
//            put("category", category)
//            put("address", address)
//            put("price", price)
//            put("working_hours", workingHours)
//            put("city", city)
//        }
//        db.insert("travel", null, values)
//        db.close()
//    }




