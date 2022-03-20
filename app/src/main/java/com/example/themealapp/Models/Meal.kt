package com.example.themealapp.Models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites_table")
@Parcelize
data class Meal(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id :Int?=null,

    @ColumnInfo(name = "meal_id")
    val idMeal: String,

    @ColumnInfo(name="meal_name")
    val strMeal: String,

    @ColumnInfo(name = "meal_image")
    val strMealThumb: String,

    @ColumnInfo(name = "isFavord")
    var isFavord:Boolean = false
):Parcelable