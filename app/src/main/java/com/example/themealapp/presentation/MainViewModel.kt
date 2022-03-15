package com.example.themealapp.presentation
import androidx.lifecycle.ViewModel
import com.example.themealapp.domain.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val mealRepo : MealRepo) : ViewModel() {

    fun getLatest()=mealRepo.getLatestMeals()

    fun getCategories()=mealRepo.getCategories()

    fun getMealsOfCategory(categoryName: String) = mealRepo.getMealOfCategory(categoryName)

    fun getSearchedMeals(searchedTxt:String) = mealRepo.getSearchedMeals(searchedTxt)

    fun getMealDetails(mealId:String) = mealRepo.getMealDetails(mealId)
}