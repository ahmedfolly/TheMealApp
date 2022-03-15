package com.example.themealapp.Models

import com.google.gson.annotations.SerializedName

data class MealsDetailsList(
    @SerializedName("meals")
    val mealsList: MutableList<MealDetails>
)
