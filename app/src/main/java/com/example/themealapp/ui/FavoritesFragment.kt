package com.example.themealapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themealapp.Models.Meal
import com.example.themealapp.R
import com.example.themealapp.databinding.FragmentFavoritesBinding
import com.example.themealapp.presentation.MainViewModel
import com.example.themealapp.ui.Adapters.DeleteFromFavorites
import com.example.themealapp.ui.Adapters.FavoritesAdapter
import com.example.themealapp.utils.Constants
import com.example.themealapp.utils.Status
import com.example.themealapp.utils.showIf


class FavoritesFragment : Fragment() , DeleteFromFavorites{


    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var favoritesList: MutableList<Meal>

    private val mViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllFavoritesMeals()
    }

    private fun getAllFavoritesMeals() {
        favoritesList= mutableListOf()
        favoritesAdapter = FavoritesAdapter(requireContext(),this)
        mViewModel.getAllFavorites().observe(viewLifecycleOwner) {

            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val meals = it.data!!
                    favoritesList.addAll(meals)
                    showEmptyFavoritesText()
                    favoritesAdapter.submitList(favoritesList)
                    setupRecyclerView()
                    Log.d(TAG, "getAllFavoritesMeals: ${favoritesList.size}")
                }
                Status.FAIL -> {}
            }
        }
    }
    private fun setupRecyclerView() {
        binding.favoritesContainer.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
            adapter=favoritesAdapter
        }
    }
    override fun deleteMeal(mealId: String,position:Int) {
        mViewModel.deleteFromFavorites(mealId)
        favoritesList.removeAt(position)
        Log.d(TAG, "deleteMeal: $favoritesList")
        binding.favoritesContainer.adapter?.notifyItemRemoved(position)
        showEmptyFavoritesText()
//        binding.favoritesContainer.adapter?.notifyItemChanged(position)
    }
    fun showEmptyFavoritesText(){
        binding.emptyFavorites.showIf {
            favoritesList.size==0
        }
    }
    companion object {
        private const val TAG = "FavoritesFragment"
    }
}