package com.example.themealapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.themealapp.Models.Category
import com.example.themealapp.databinding.FragmentCategories2Binding
import com.example.themealapp.presentation.MainViewModel
import com.example.themealapp.ui.Adapters.MealsAdapter
import com.example.themealapp.utils.Status
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class FilterCategoriesFragment : Fragment() {
    private lateinit var categoriesList: List<Category>
    private lateinit var mealsAdapter: MealsAdapter
    private var _binding: FragmentCategories2Binding? = null
    private val binding
        get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealsAdapter = MealsAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategories2Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
        initVars()

    }

    private fun getArgsFromMainFragment() {
        val categoryPosition = Integer.parseInt(mainViewModel.getCategory().idCategory)
        Log.d(TAG, "getArgsFromMainFragment: ${mainViewModel.getCategory().idCategory}")
        CoroutineScope(Dispatchers.Main).launch {
            selectTab(categoryPosition - 1)
        }
    }

    private suspend fun selectTab(position: Int) {
        delay(5)
        binding.tabLayout.getTabAt(position)?.select()
    }

    private fun getCategories() {
        mainViewModel.getCategories().observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                binding.nestedScrollView.visibility = View.VISIBLE
                categoriesList = it.data!!
                for (category: Category in categoriesList) {
                    binding.tabLayout.addTab(
                        binding.tabLayout.newTab().setText(category.strCategory)
                    )
                }
                getArgsFromMainFragment()

                onTabSelected(categoriesList)
            }
        }
    }

    private fun initVars() {
        getCategoryInfo()
    }

    private fun getCategoryInfo() {
        val categoryDesc = mainViewModel.getCategory().strCategoryDescription
        val categoryName = mainViewModel.getCategory().strCategory
        getMealsByCategoryName(categoryName)
        setCategoryDesc(categoryDesc)
    }

    private fun setCategoryDesc(categoryDesc: String) {
        binding.categoryDescInFragment.text = categoryDesc
    }

    private fun onTabSelected(categoriesList: List<Category>) {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                doInTabSelected(tab!!, categoriesList)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun getMealsByCategoryName(categoryName: String) {
        mainViewModel.getMealsOfCategory(categoryName).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.shimmerEffect3.startShimmer()
                }
                Status.SUCCESS -> {
                    val mealsList = it.data
                    mealsAdapter.submitList(mealsList)
                    binding.shimmerEffect3.visibility = View.INVISIBLE
                }
                Status.FAIL -> {
                }
            }
        }
        setupMealsContainer()
    }

    private fun setupMealsContainer() {
        binding.mealsContainerId.apply {
            setHasFixedSize(true)
            adapter = mealsAdapter
        }
    }

    private fun doInTabSelected(tab: TabLayout.Tab, categoriesList: List<Category>) {
        binding.nestedScrollView.scrollTo(0, 0)
        binding.shimmerEffect3.startShimmer()
        for (categoryItem: Category in categoriesList) {
            if (categoryItem.idCategory == "${tab.position.plus(1)}") {
                mainViewModel.setCategory(categoryItem)
                val categoryDesc = categoryItem.strCategoryDescription
                setCategoryDesc(categoryDesc)
                getMealsByCategoryName(categoryItem.strCategory)
                binding.shimmerEffect3.visibility = View.INVISIBLE

            }
        }
    }

    companion object {
        private const val TAG = "Categories2Fragment"
    }
}