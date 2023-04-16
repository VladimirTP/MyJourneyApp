package com.example.myjourneyapp.ui.main.fragments.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myjourneyapp.MyApp
import com.example.myjourneyapp.R
import com.example.myjourneyapp.databinding.FragmentFavoritesBinding
import com.example.myjourneyapp.di.ViewModelFactory
import com.example.myjourneyapp.ui.main.fragments.home.adapters.FavoritesMealAdapter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoritesViewModel by viewModels { factory }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private var favoritesAdapter: FavoritesMealAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyApp).appComponent.inject(this@FavoritesFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        viewModel.getSavedMeals()

        viewModel.favoritesLiveData.observe(viewLifecycleOwner) {favoritesMealDataList ->

            if (favoritesMealDataList.isEmpty()) {
                binding.tvFavoritesMealIsEmpty.isVisible = true
                binding.rvFavorites.isVisible = false
            } else {
                binding.tvFavoritesMealIsEmpty.isVisible = false
                binding.rvFavorites.isVisible = true

                favoritesAdapter?.differ?.submitList(favoritesMealDataList)

                val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ) = true

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        viewModel.deleteMeal(
                            favoritesAdapter?.differ?.currentList?.get(position)
                                ?: throw Exception()
                        )
                        Snackbar.make(requireView(), getString((R.string.meal_deleted)), Snackbar.LENGTH_LONG)
                            .setAction(
                                getString((R.string.undo))
                            ) {
                                viewModel.saveFavoriteMeal(
                                    favoritesAdapter?.differ?.currentList?.get(
                                        position
                                    ) ?: throw Exception()
                                )
                            }.show()
                    }
                }
                ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
            }
        }
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesMealAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}