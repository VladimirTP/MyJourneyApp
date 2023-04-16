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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myjourneyapp.MyApp
import com.example.myjourneyapp.R
import com.example.myjourneyapp.databinding.FragmentMealBinding
import com.example.myjourneyapp.di.ViewModelFactory
import com.example.myjourneyapp.domain.models.MealsData
import com.example.myjourneyapp.ui.main.fragments.favorites.FavoritesViewModel
import javax.inject.Inject

class MealFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MealInfoByIdViewModel by viewModels { factory }
    private val favoritesViewModel: FavoritesViewModel by viewModels { factory }

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!

    private val args: MealFragmentArgs by navArgs()

    private var mealToSafe: MealsData? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyApp).appComponent.inject(this@MealFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealId = args.mealId
        val mealName = args.mealName
        val imageUrl = args.imageUrl

        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.ivRandomMeal)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white, requireActivity().theme))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white, requireActivity().theme))

        viewModel.getMealInfoById(mealId)

        onFavoriteClick()

        viewModel.liveDataById.observe(viewLifecycleOwner) {mealsDataList ->
            mealsDataList.firstOrNull()?.let {mealsData ->
                with(binding) {
                    tvInstructionDetails.text = mealsData.mealDetails
                    tvCategory.text = getString(R.string.category, mealsData.mealCategory)
                    tvArea.text = getString(R.string.area, mealsData.mealArea)
                }
                mealToSafe = mealsData
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onFavoriteClick() {
        binding.btAddToFavorites.setOnClickListener {
            mealToSafe?.let {
                favoritesViewModel.saveFavoriteMeal(it)
                Toast.makeText(requireContext(), getString(R.string.meal_saved), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}