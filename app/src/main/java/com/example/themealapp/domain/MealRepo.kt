package com.example.themealapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.themealapp.Models.*
import com.example.themealapp.api.APIService
import com.example.themealapp.api.RetrofitBuilder
import com.example.themealapp.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class MealRepo @Inject constructor(private val apiService: APIService) {

    fun getLatestMeals() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getLatestMeals()
            emit(Resource.success(data = response.body()?.mealsList))
        } catch (exception: Exception) {
            Resource.failed<MealsDetailsList>(null, exception.message.toString())
        }
    }

    fun getCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                emit(Resource.success(response.body()?.categories))
            }
        } catch (exception: Exception) {
            Resource.failed<MealsDetailsList>(null, exception.message.toString())
        }
    }

    fun getMealOfCategory(categoryName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val mealsList = apiService.getMealsByCategory(categoryName).body()?.meals
            emit(Resource.success(mealsList))
        } catch (exception: Exception) {
            emit(Resource.failed(null, exception.message.toString()))
        }
    }

    fun getSearchedMeals(searchedText: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val data = apiService.getSearchedMeals(searchedText).body()?.mealsList
            emit(Resource.success(data))

        } catch (exception: Exception) {
            Resource.failed(data = null, exception.message.toString())
        }
    }

    fun getMealDetails(mealId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val meal=apiService.getMealDetails(mealId).body()
            emit(Resource.success(meal!!.mealsList))
        } catch (exception: Exception) {
            emit(Resource.failed(null, exception.message.toString()))
        }
    }
}