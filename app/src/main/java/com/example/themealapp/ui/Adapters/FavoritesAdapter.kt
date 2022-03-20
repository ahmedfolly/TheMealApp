package com.example.themealapp.ui.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themealapp.Models.Meal
import com.example.themealapp.R
import com.example.themealapp.databinding.FavoriteMealLayoutBinding
import com.example.themealapp.ui.FavoritesFragmentDirections
import com.example.themealapp.utils.Constants

class FavoritesAdapter(val context: Context,val deleteFromFavorites: DeleteFromFavorites) : ListAdapter<Meal, FavoritesAdapter.FavoritesVH>(FavoritesDiffUtils()) {

    private var _binding: FavoriteMealLayoutBinding? = null
    private val binding
        get() = _binding!!

    var isFavord : Boolean = true
    lateinit var navController: NavController

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesVH {
        val inflater= LayoutInflater.from(parent.context)
        _binding= FavoriteMealLayoutBinding.inflate(inflater,parent,false)
        return FavoritesVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesVH, position: Int) {
        val favoriteMeal = getItem(position)
        holder.binds(favoriteMeal)
        if (isFavord){
            holder.binding.selectUnfavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            holder.binding.selectUnfavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        holder.itemView.setOnClickListener {
            navController=Navigation.findNavController(it)
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(favoriteMeal)
            navController.navigate(action)
        }

    }
    inner class FavoritesVH( val binding: FavoriteMealLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binds(favoriteMeal:Meal){
            val favoriteMealName=favoriteMeal.strMeal
            val favoriteMealImageUrl=favoriteMeal.strMealThumb

            binding.favoriteMealName.text=favoriteMealName
            Glide.with(context).load(favoriteMealImageUrl).into(binding.favoriteImageId)

            onFavoriteBtnClicked(favoriteMeal)
        }
        private fun onFavoriteBtnClicked(favoriteMeal:Meal){
            binding.selectUnfavorite.setOnClickListener {
                binding.selectUnfavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                isFavord=true
                deleteFromFavorites.deleteMeal(favoriteMeal.idMeal,adapterPosition)
            }
        }
    }
    companion object{
        private const val TAG = "FavoritesAdapter"
    }
}
class FavoritesDiffUtils : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }

}
interface DeleteFromFavorites{
    fun deleteMeal(mealId:String,position: Int)
}
