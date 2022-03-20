package com.example.themealapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.example.themealapp.Models.*
import com.example.themealapp.R
import com.example.themealapp.databinding.FragmentMainBinding
import com.example.themealapp.presentation.MainViewModel
import com.example.themealapp.ui.Adapters.CategoriesAdapter
import com.example.themealapp.ui.Adapters.Favorite
import com.example.themealapp.ui.Adapters.LatestMealsAdapter
import com.example.themealapp.ui.Adapters.PutCategory
import com.example.themealapp.utils.Status
import com.example.themealapp.utils.showIf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(),PutCategory {

    private val navController by lazy {
        findNavController()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var latestMealsList: MutableList<Meal>
    private lateinit var latestMealsAdapter: LatestMealsAdapter

    private lateinit var categoriesList: MutableList<Category>
    private lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var favoritesList: MutableList<Meal>
    val mViewModel by activityViewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        getLatestMeals()
        getCategories()
        moveToSearchScreen()
        openFavoritesScreen()

    }

    private fun initVars() {
        latestMealsList = mutableListOf()
        favoritesList = mutableListOf()
        latestMealsAdapter = LatestMealsAdapter(requireContext(), latestMealsList)

        categoriesList = mutableListOf()
        categoriesAdapter = CategoriesAdapter(requireContext(),this)

    }

    private fun getLatestMeals() {
        mViewModel.getLatest().observe(viewLifecycleOwner) {
            binding.shimmerViewContainer.showIf {
                it.data == null
            }
            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    it.data.let { latestMeals ->
                        latestMealsList.addAll(latestMeals!!)
                        latestMealsAdapter.notifyItemInserted(latestMeals.size)
                    }
                }
                Status.FAIL -> {}
            }
        }
        initLatestRecycler()
    }

    private fun initLatestRecycler() {
        val snapHelper = LinearSnapHelper()
        binding.latestMealsRecycler.apply {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            adapter = latestMealsAdapter
        }
    }

    private fun getCategories() {

        mViewModel.getCategories().observe(viewLifecycleOwner) {
            binding.categoiresShimmerEffect.showIf {
                it.data == null
            }
            when (it.status) {
                Status.LOADING -> {
//                    binding.categoiresShimmerEffect.startShimmer()
                }
                Status.SUCCESS -> {
                    categoriesAdapter.submitList(it.data)
//                    binding.categoiresShimmerEffect.visibility=View.INVISIBLE
                }
                Status.FAIL -> {}
            }
        }
        initCategoriesRecycler()
    }

    private fun initCategoriesRecycler() {
        binding.categoriesRecycler.apply {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoriesAdapter
        }
    }

    private fun moveToSearchScreen() {
        binding.searchBoxMainId.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    private fun openFavoritesScreen() {
        binding.showFavorites.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_favoritesFragment)
        }
    }

    override fun setCategory(category: Category) {
        mViewModel.setCategory(category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}