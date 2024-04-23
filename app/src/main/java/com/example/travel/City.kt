package com.example.travel

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City (
    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int,
    @ColumnInfo(name = "name_city") @NonNull
    val name_city: String
)