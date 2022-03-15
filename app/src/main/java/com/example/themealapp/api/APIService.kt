package com.example.themealapp.api

import com.example.themealapp.Models.Categories
import com.example.themealapp.Models.MealDetails
import com.example.themealapp.Models.Meals
import com.example.themealapp.Models.MealsDetailsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    //    suspend fun getLatestMeals():Response<MealsDetailsList>
    @GET("latest.php")
    suspend fun getLatestMeals(): Response<MealsDetailsList>

    @GET("categories.php")
    suspend fun getCategories(): Response<Categories>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName: String): Response<Meals>

    @GET("search.php")
    suspend fun getSearchedMeals(@Query("s") searchedText: String): Response<MealsDetailsList>

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId:String) : Response<MealsDetailsList>
}