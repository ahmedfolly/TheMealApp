package com.example.themealapp.ui.Adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.Models.Meal
import com.example.themealapp.Models.MealDetails
import com.example.themealapp.R
import com.example.themealapp.databinding.SearchedMealLayoutBinding
import com.example.themealapp.ui.SearchFragmentDirections


class SearchedMealsAdapter(val context: Context) :
    ListAdapter<Meal, SearchedMealsAdapter.SearchedMealsVH>(SearchedMealsListDiff()) {

    //    var searchedTerm:String?=null
    private var _binding: SearchedMealLayoutBinding?=null
    private val binding
        get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):SearchedMealsVH {
        val inflater = LayoutInflater.from(parent.context)
        _binding= SearchedMealLayoutBinding.inflate(inflater,parent,false)
        return SearchedMealsVH(binding)
    }

    override fun onBindViewHolder(holder: SearchedMealsAdapter.SearchedMealsVH, position: Int) {
        val meal = getItem(position)
        val searchedMealImageUrl = meal.strMealThumb
        val searchedMealName = meal.strMeal

        holder.binding.searchedMealName.text = searchedMealName
        Glide.with(context).load(searchedMealImageUrl).into(holder.binding.searchedMealImage)

        holder.itemView.setOnClickListener {
            navController=Navigation.findNavController(it)
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal)
            navController.navigate(action)
        }
    }

    class SearchedMealsVH(val binding: SearchedMealLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    companion object {
        private const val TAG = "SearchedMealsAdapter"
    }

    class SearchedMealsListDiff : DiffUtil.ItemCallback<Meal>() {
        //        override fun areItemsTheSame(oldItem: MealDetails, newItem: MealDetails): Boolean {
//            return oldItem.idMeal == newItem.idMeal
//        }
//
//        override fun areContentsTheSame(oldItem: MealDetails, newItem: MealDetails): Boolean {
//            return oldItem == newItem
//        }
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }
}
