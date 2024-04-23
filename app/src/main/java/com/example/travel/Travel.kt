package com.example.travel

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "travel")
data class Travel (
    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int,
    @ColumnInfo(name = "name_place") @NonNull
    val name_place: String,
    @ColumnInfo(name = "category") @NonNull
    val category: String,
    @ColumnInfo(name = "address") @NonNull
    val address: String,
    @ColumnInfo(name = "price") @NonNull
    val price: Int,
    @ColumnInfo(name = "working_hours") @NonNull
    val working_hours: String,
    @ColumnInfo(name = "city_id") @NonNull
    val city_id: Int,
    @ColumnInfo(name = "image_path") @NonNull
    val image_path: String,
    @ColumnInfo(name = "liked", defaultValue = "0") @NonNull
    val liked: Int
)