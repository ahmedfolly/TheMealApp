package com.example.themealapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.themealapp.Models.Meal
import com.example.themealapp.Models.MealDetails
import com.example.themealapp.R
import com.example.themealapp.databinding.FragmentDetailsBinding
import com.example.themealapp.presentation.MainViewModel
import com.example.themealapp.utils.Status
import com.example.themealapp.utils.hideKeyboard
import com.example.themealapp.utils.showIf
import java.lang.StringBuilder

class DetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var favorites: List<Meal>
    private lateinit var newList: MutableList<String>
    private var isFavorite: Boolean = false
    private var categoryName:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        hideKeyboard()
        getMealDetails(args.meal!!.idMeal)
        getFavorites()
        onFavoriteBtnClicked()

    }
    private fun getMealDetails(mealId: String) {
        mainViewModel.getMealDetails(mealId).observe(viewLifecycleOwner) {
            val meals = it.data
            binding.progressBar.showIf {
                meals == null
            }
            if (it.status == Status.SUCCESS) {
                val meal = meals!![0]
                setupVariables(meal)
                openLink(meal)
                openYoutube(meal)
            }
        }
    }
    private fun putMealImage(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(binding.mealThumb)
    }
    private fun putMealText(mealName: String) {
        binding.mealName.text = mealName
    }
    private fun putInstructions(instructionText: String) {
        binding.instructions.text = instructionText
    }
    private fun putCategoryName(categoryName: String) {
        binding.category.text = categoryName
    }
    private fun putCountryName(countryName: String) {
        binding.country.text = countryName
    }
    private fun putIngredient(gradients: String) {
        binding.ingredient.text = gradients
    }
    private fun getIngredient(ingredients: MutableList<String>) {
        val ingredientText = StringBuilder()
        for (ingredient: String in ingredients) {
            if (ingredient != " " && ingredient.isNotEmpty()) {
                ingredientText.append("\n \u2022$ingredient")
            }
            putIngredient(ingredientText.toString())
        }
    }
    private fun addIngredientToIngredients(meal: MealDetails) {
        val ingredients = mutableListOf<String>()
        ingredients.add(meal.strIngredient1)
        ingredients.add(meal.strIngredient2)
        ingredients.add(meal.strIngredient3)
        ingredients.add(meal.strIngredient4)
        ingredients.add(meal.strIngredient5)
        ingredients.add(meal.strIngredient6)
        ingredients.add(meal.strIngredient7)
        ingredients.add(meal.strIngredient8)
        ingredients.add(meal.strIngredient9)
        ingredients.add(meal.strIngredient10)
        ingredients.add(meal.strIngredient11)
        ingredients.add(meal.strIngredient12)
        ingredients.add(meal.strIngredient13)
        ingredients.add(meal.strIngredient14)
        ingredients.add(meal.strIngredient15)
        ingredients.add(meal.strIngredient16)
        ingredients.add(meal.strIngredient17)
        ingredients.add(meal.strIngredient18)
        ingredients.add(meal.strIngredient19)
        ingredients.add(meal.strIngredient20)

        getIngredient(ingredients)
    }
    private fun addMeasures(meal: MealDetails) {

        val measures = mutableListOf<String>()
        measures.add(meal.strMeasure1)
        measures.add(meal.strMeasure2)
        measures.add(meal.strMeasure3)
        measures.add(meal.strMeasure4)
        measures.add(meal.strMeasure5)
        measures.add(meal.strMeasure6)
        measures.add(meal.strMeasure7)
        measures.add(meal.strMeasure8)
        measures.add(meal.strMeasure9)
        measures.add(meal.strMeasure10)
        measures.add(meal.strMeasure11)
        measures.add(meal.strMeasure12)
        measures.add(meal.strMeasure13)
        measures.add(meal.strMeasure14)
        measures.add(meal.strMeasure15)
        measures.add(meal.strMeasure16)
        measures.add(meal.strMeasure17)
        measures.add(meal.strMeasure18)
        measures.add(meal.strMeasure19)
        measures.add(meal.strMeasure20)

        getMeasures(measures)
    }
    private fun getMeasures(measuresList: MutableList<String>) {
        val measureText = StringBuilder()
        for (measure: String in measuresList) {
            if (measure != " " && measure.isNotEmpty()) {
                measureText.append("\n \u2022$measure")
            }
            putMeasuresText(measureText.toString())
        }
    }
    private fun putMeasuresText(measureText: String) {
        binding.measure.text = measureText
    }
    private fun setupVariables(meal: MealDetails) {
        val imageUrl = meal.strMealThumb
        putMealImage(imageUrl)
        val mealName = meal.strMeal
        putMealText(mealName)
        val instructionText = meal.strInstructions
        putInstructions(instructionText)
        categoryName = meal.strCategory
        putCategoryName(categoryName!!)
        val countryName = meal.strArea
        putCountryName(countryName)

        addIngredientToIngredients(meal)
        addMeasures(meal)

    }
    private fun openLink(meal: MealDetails) {
        binding.source.setOnClickListener {
            implicitIntent(meal.strSource)
        }
    }
    private fun openYoutube(meal: MealDetails) {
        binding.youtube.setOnClickListener {
            implicitIntent(meal.strYoutube)
        }
    }
    private fun implicitIntent(uri: String) {
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity?.startActivity(implicitIntent)
    }
    private fun addMealToFavorites(meal: Meal) {
        mainViewModel.addMealToFavorites(meal).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    Toast.makeText(context, "Saved to favorites", Toast.LENGTH_SHORT).show()
                }
                Status.FAIL -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getFavorites() {
        newList = mutableListOf()
        favorites = listOf()
        mainViewModel.getAllFavorites().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    favorites = resource.data!!
                    for (meal:Meal in favorites) {
                        newList.add(meal.idMeal)
                    }
                    isFavorite = if (newList.contains(args.meal!!.idMeal)) {
                        addFilledFavoriteImage()
                        true
                    } else {
                        addOutlinedFavoriteImage()
                        false
                    }
                }
                Status.FAIL -> {}
            }
        }
    }
    private fun onFavoriteBtnClicked(){
        binding.addMealToFavorites.setOnClickListener {
            isFavorite = if (!isFavorite){
                binding.addMealToFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                args.meal?.isFavord=true
                addMealToFavorites(args.meal!!)
                true
            }else{
                binding.addMealToFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                mainViewModel.deleteFromFavorites(args.meal!!.idMeal)
                false
            }
        }
    }
    private fun addFilledFavoriteImage(){
        binding.addMealToFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
    }
    private fun addOutlinedFavoriteImage(){
        binding.addMealToFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}