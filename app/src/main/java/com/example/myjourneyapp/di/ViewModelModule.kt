package com.example.myjourneyapp.di

import androidx.lifecycle.ViewModel
import com.example.myjourneyapp.ui.main.fragments.favorites.FavoritesViewModel
import com.example.myjourneyapp.ui.main.fragments.home.MealInfoByIdViewModel
import com.example.myjourneyapp.ui.main.fragments.home.MealsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MealsViewModel::class)
    fun bindMealsViewModel(viewModel: MealsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealInfoByIdViewModel::class)
    fun bindMealInfoByIdViewModel(viewModel: MealInfoByIdViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel
}