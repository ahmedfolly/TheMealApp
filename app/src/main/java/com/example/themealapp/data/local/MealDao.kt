package com.example.themealapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.themealapp.Models.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert
    suspend fun insertMealToFavorites(meal:Meal)

    @Query("Select * from favorites_table order by id DESC")
    suspend fun getAllFavorites(): List<Meal>

    @Query("Delete from favorites_table where meal_id like :mealId")
    suspend fun deleteMealFromFavorites(mealId:String)

}