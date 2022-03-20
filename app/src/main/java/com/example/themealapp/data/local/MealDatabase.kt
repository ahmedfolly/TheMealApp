package com.example.themealapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themealapp.Models.Meal

@Database(entities = [Meal::class], version = 5)
abstract class MealDatabase : RoomDatabase() {
    abstract fun getMealDao():MealDao
}