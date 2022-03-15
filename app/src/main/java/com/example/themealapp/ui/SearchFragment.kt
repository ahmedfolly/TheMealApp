package com.example.themealapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.themealapp.Models.MealDetails
import com.example.themealapp.databinding.FragmentSearchBinding
import com.example.themealapp.presentation.MainViewModel
import com.example.themealapp.ui.Adapters.SearchedMealsAdapter
import com.example.themealapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var searchedMealsAdapter: SearchedMealsAdapter

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        searchAction()
        initRecyclerView()
    }

    private fun initVars() {
        showKeyboard(requireContext(), binding.searchBoxId)
        searchedMealsAdapter = SearchedMealsAdapter(requireContext())
    }

    private fun searchAction() {
        binding.searchBoxId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                mainViewModel.getSearchedMeals(p0.toString()).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.searchProgressBar.show()
                            binding.view.show()
                        }
                        Status.SUCCESS -> {
                            val mealsList = it.data
                            binding.noMealsText.showIf {
                                mealsList == null
                            }
                            searchedMealsAdapter.submitList(mealsList)
                            binding.searchProgressBar.hide()
                            binding.view.hide()
                        }
                        Status.FAIL -> {/*do nothing*/
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun initRecyclerView() {
        binding.searchedMealsContainer.apply {
            setHasFixedSize(true)
            adapter = searchedMealsAdapter
        }
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}

