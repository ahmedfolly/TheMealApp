package com.example.themealapp.data.remote

import com.example.themealapp.Models.Categories
import com.example.themealapp.Models.Meal
import com.example.themealapp.Models.Meals
import com.example.themealapp.Models.MealsDetailsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("latest.php")
    suspend fun getLatestMeals(): Response<Meals>

    @GET("categories.php")
    suspend fun getCategories(): Response<Categories>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName: String): Response<Meals>

    @GET("search.php")
    suspend fun getSearchedMeals(@Query("s") searchedText: String): Response<Meals>

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId:String) : Response<MealsDetailsList>
}