package com.example.themealapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themealapp.Models.Category
import com.example.themealapp.Models.Meal
import com.example.themealapp.domain.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mealRepo: MealRepo) : ViewModel() {
    private var category : Category?=null


    fun getLatest() = mealRepo.getLatestMeals()

    fun getCategories() = mealRepo.getCategories()

    fun getMealsOfCategory(categoryName: String) = mealRepo.getMealOfCategory(categoryName)

    fun getSearchedMeals(searchedTxt: String) = mealRepo.getSearchedMeals(searchedTxt)

    fun getMealDetails(mealId: String) = mealRepo.getMealDetails(mealId)

    fun addMealToFavorites(meal: Meal) = mealRepo.addMealToFavorites(meal)

    fun getAllFavorites() = mealRepo.getAllFavorites()

    fun deleteFromFavorites(mealId: String){
        viewModelScope.launch(Dispatchers.IO){
            mealRepo.deleteFromFavorites(mealId)
        }
    }

    fun setCategory(category: Category){
        this.category=category
    }
    fun getCategory():Category{
        return category!!
    }

}