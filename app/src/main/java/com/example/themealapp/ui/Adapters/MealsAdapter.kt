package com.example.themealapp.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.Models.Category
import com.example.themealapp.Models.Meal
import com.example.themealapp.R
import com.example.themealapp.ui.FilterCategoriesFragment
import com.example.themealapp.ui.FilterCategoriesFragmentDirections

class MealsAdapter(val context: Context) :
    androidx.recyclerview.widget.ListAdapter<Meal, MealsAdapter.MealsVH>(MealListDiff()) {

    private lateinit var navController: NavController
    private lateinit var category: Category
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsAdapter.MealsVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.meal_item_layout, parent, false)
        return MealsVH(view)
    }

    override fun onBindViewHolder(holder: MealsAdapter.MealsVH, position: Int) {
        val meal = getItem(position)
        val mealImageUrl = meal.strMealThumb
        val mealName = meal.strMeal

        holder.mealName.text = mealName
        Glide.with(context).load(mealImageUrl).into(holder.mealImage)

        holder.itemView.setOnClickListener {
            navController=Navigation.findNavController(it)
            val action = FilterCategoriesFragmentDirections.actionCategories2FragmentToDetailsFragment(meal.idMeal,category)
            navController.navigate(action)
        }
    }


    class MealsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ImageView = itemView.findViewById(R.id.meal_image_id)
        val mealName: TextView = itemView.findViewById(R.id.meal_text_id)
    }
     fun navigate(category:Category){
        this.category=category
    }
}

class MealListDiff : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }

}