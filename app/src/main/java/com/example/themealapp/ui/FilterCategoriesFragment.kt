package com.example.themealapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.themealapp.Models.Category
import com.example.themealapp.databinding.FragmentCategories2Binding
import com.example.themealapp.presentation.MainViewModel
import com.example.themealapp.ui.Adapters.MealsAdapter
import com.example.themealapp.utils.Status
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*


@AndroidEntryPoint
class FilterCategoriesFragment : Fragment() {

    private val categories2FragmentArgs: FilterCategoriesFragmentArgs by navArgs()
    lateinit var category: Category
    private lateinit var categoriesList: List<Category>
    private lateinit var mealsAdapter: MealsAdapter
    private var _binding: FragmentCategories2Binding? = null
    private val binding
        get() = _binding!!

    var  categoryPosition:Int?=null
    lateinit var navController: NavController

    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealsAdapter = MealsAdapter(requireContext())

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action=FilterCategoriesFragmentDirections.actionCategories2FragmentToMainFragment()
            navController.navigate(action)
        }
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
        navController=Navigation.findNavController(view)
        category = categories2FragmentArgs.category!!
        mealsAdapter.navigate(category)
        getCategories()
        initVars()
    }

    private fun scrollToPosition() {
        categoryPosition = Integer.parseInt(category.idCategory)
        CoroutineScope(Dispatchers.Main).launch {
            selectCategory(categoryPosition!! - 1)
        }
    }

    private suspend fun selectCategory(position: Int) {
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
                scrollToPosition()
                onTabSelected(categoriesList)
            }
        }
    }

    private fun initVars() {
        getCategoryDesc()
    }

    private fun getCategoryDesc() {
        val categoryDesc = category.strCategoryDescription
        val categoryName = category.strCategory
        getMealsByCategoryName(categoryName)
        changeCategoryDesc(categoryDesc)
    }

    private fun changeCategoryDesc(categoryDesc: String) {
        binding.categoryDescInFragment.text = categoryDesc
    }

    private fun onTabSelected(categoriesList: List<Category>) {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.nestedScrollView.scrollTo(0, 0)
                for (categoryItem: Category in categoriesList) {
                    if (categoryItem.idCategory == "${tab?.position?.plus(1)}") {
                        category=categoryItem
                        mealsAdapter.navigate(category)
                        val categoryDesc = categoryItem.strCategoryDescription
                        changeCategoryDesc(categoryDesc)
                        getMealsByCategoryName(categoryItem.strCategory)
                    }
                }
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
                    val mealsList=it.data
                    mealsAdapter.submitList(mealsList)
                    binding.shimmerEffect3.visibility=View.INVISIBLE
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

    companion object {
        private const val TAG = "Categories2Fragment"
    }
}
