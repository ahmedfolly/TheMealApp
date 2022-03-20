package com.example.themealapp.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.themealapp.Models.Meal
import com.example.themealapp.Models.MealDetails
import com.example.themealapp.Models.MealsDetailsList
import com.example.themealapp.R
import com.example.themealapp.databinding.LatestMealLayoutBinding
import com.example.themealapp.ui.MainFragmentDirections

class LatestMealsAdapter(
    private val context: Context,
    private val latestMealsList: MutableList<Meal>
    ) : RecyclerView.Adapter<LatestMealsAdapter.LatestMealsVH>() {

    private var _binding: LatestMealLayoutBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestMealsVH {
        val inflater = LayoutInflater.from(parent.context)
        _binding = LatestMealLayoutBinding.inflate(inflater, parent, false)
        return LatestMealsVH(binding)
    }

    override fun onBindViewHolder(holder: LatestMealsVH, position: Int) {
        val meal = latestMealsList[position]
        val latestImageStr = meal.strMealThumb
        val latestMealName = meal.strMeal

        Glide.with(context).load(latestImageStr).into(holder.binding.latestMealImage)
        holder.binding.latestMealName.text = latestMealName

        holder.itemView.setOnClickListener {
            navController = Navigation.findNavController(it)
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(meal)
            navController.navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return latestMealsList.size
    }

    class LatestMealsVH(val binding: LatestMealLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}

interface Favorite {
    fun isFavorite(meal: Meal, position: Int)
}