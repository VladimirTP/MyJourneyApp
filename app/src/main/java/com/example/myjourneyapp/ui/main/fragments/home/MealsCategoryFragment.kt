package com.example.myjourneyapp.ui.main.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myjourneyapp.MyApp
import com.example.myjourneyapp.databinding.FragmentMealsCategoryBinding
import com.example.myjourneyapp.di.ViewModelFactory
import com.example.myjourneyapp.ui.main.fragments.home.adapters.MealFromCategoryAdapter
import javax.inject.Inject

class MealsCategoryFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: MealsViewModel by viewModels { factory }

    private var _binding: FragmentMealsCategoryBinding? = null
    private val binding get() = _binding!!

    private val args: MealsCategoryFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyApp).appComponent.inject(this@MealsCategoryFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealNameFromCategory = args.name

        viewModel.getMealByCategory(mealNameFromCategory)

        viewModel.mealsByCategoryLiveData.observe(viewLifecycleOwner) { mealsFromCategoryList ->
            mealsFromCategoryList.firstOrNull()?.let {

                val itemClick: (String, String, String) -> Unit = {mealId, mealName, imageUrl ->
                    val action = MealsCategoryFragmentDirections.actionMealsCategoryFragmentToMealFragment(mealId, mealName, imageUrl)
                    findNavController().navigate(action)
                }

                val adapter = MealFromCategoryAdapter(mealsFromCategoryList, itemClick)
                binding.rvCategoriesDetails.adapter = adapter
                binding.rvCategoriesDetails.layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}