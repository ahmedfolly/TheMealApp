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
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.Models.Category
import com.example.themealapp.R
import com.example.themealapp.ui.MainFragmentDirections

class CategoriesAdapter(
    val context: Context
) : ListAdapter<Category, CategoriesAdapter.CategoriesVH>(MealsListDiffUtil()) {

    private lateinit var navController: NavController

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoriesVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoriesVH(view)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.CategoriesVH, position: Int) {
        val category = getItem(position)
        val categoryImageUrl = category.strCategoryThumb
        val categoryName = category.strCategory

        Glide.with(context).load(categoryImageUrl).into(holder.categoryImage)
        holder.categoryName.text = categoryName
        holder.itemView.setOnClickListener {
            initCategoryFragment(it, category)
        }
    }

    class CategoriesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage: ImageView = itemView.findViewById(R.id.category_image)
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
    }

    private fun initCategoryFragment(view: View, category: Category) {
        navController = Navigation.findNavController(view)
        val action = MainFragmentDirections.actionMainFragmentToCategories2Fragment(category,null)
        navController.navigate(action)
    }

    companion object {
        const val EXTRA_CATEGORY_POSITION =
            "com.example.themealapp.ui.Adapters.CategoriesAdapter.CATEGORY_POSITION"
    }
}

class MealsListDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}