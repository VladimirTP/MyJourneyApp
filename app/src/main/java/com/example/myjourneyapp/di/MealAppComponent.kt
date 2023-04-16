package com.example.myjourneyapp.di

import android.content.Context
import com.example.myjourneyapp.ui.main.fragments.favorites.FavoritesFragment
import com.example.myjourneyapp.ui.main.fragments.home.MainFragment
import com.example.myjourneyapp.ui.main.fragments.home.MealFragment
import com.example.myjourneyapp.ui.main.fragments.home.MealsCategoryFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule:: class, NetworkModule:: class, ViewModelModule::class, DataBaseModule::class])
interface MealAppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MealAppComponent
    }

    fun inject(mainFragment: MainFragment)
    fun inject(mealsCategoryFragment: MealsCategoryFragment)
    fun inject(mealFragment: MealFragment)
    fun inject(favoritesFragment: FavoritesFragment)
}