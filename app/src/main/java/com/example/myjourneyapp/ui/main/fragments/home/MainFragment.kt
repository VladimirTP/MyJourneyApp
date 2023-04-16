package com.example.myjourneyapp.ui.main.fragments.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myjourneyapp.MyApp
import com.example.myjourneyapp.databinding.FragmentMainBinding

import com.example.myjourneyapp.di.ViewModelFactory
import com.example.myjourneyapp.ui.main.fragments.home.adapters.MealCategoriesAdapter
import com.example.myjourneyapp.ui.main.fragments.home.adapters.MealPopularAdapter
import javax.inject.Inject

class MainFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MealsViewModel by viewModels { factory }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyApp).appComponent.inject(this@MainFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomMeal()

        viewModel.liveData.observe(viewLifecycleOwner) { mealsDataList ->
            mealsDataList.firstOrNull()?.let { mealsData ->
                Glide.with(this@MainFragment)
                    .load(mealsData.imageUrl)
                    .into(binding.ivRandomMeal)

                val mealId = mealsData.idMeal
                val mealName = mealsData.mealName
                val imageUrl = mealsData.imageUrl

                binding.randomMealCard.setOnClickListener {
                    val action = MainFragmentDirections.actionMainFragmentToMealFragment(mealId, mealName, imageUrl)
                    findNavController().navigate(action)
                }
            }
        }

        viewModel.getPopularItems()

        viewModel.popularMealsLiveData.observe(viewLifecycleOwner) {mealsPopularDataList ->
            mealsPopularDataList.firstOrNull()?.let {
                val itemClick: (String, String, String) -> Unit = {mealId, mealName, imageUrl ->
                    val action = MainFragmentDirections.actionMainFragmentToMealFragment(mealId, mealName, imageUrl)
                    findNavController().navigate(action)
                }
                val adapter = MealPopularAdapter(mealsPopularDataList, itemClick)
                binding.rvMealsPopular.adapter = adapter
                binding.rvMealsPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.getCategories()

        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { mealsCategoryDataList ->
            mealsCategoryDataList.firstOrNull()?.let {
                val itemClick: (String, String) -> Unit = { url, name ->
                    val action = MainFragmentDirections.actionMainFragmentToMealsCategoryFragment(name, url)
                    findNavController().navigate(action)
                }
                val adapter = MealCategoriesAdapter(mealsCategoryDataList, itemClick)
                binding.rvViewCategories.adapter = adapter
                binding.rvViewCategories.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}