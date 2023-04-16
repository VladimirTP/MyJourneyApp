package com.example.myjourneyapp.ui.main.fragments.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjourneyapp.domain.MealsRepository
import com.example.myjourneyapp.domain.models.MealsData
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel@Inject constructor(private val repository: MealsRepository) : ViewModel() {

    private val _favoritesLiveData = MutableLiveData<List<MealsData>>()
    val favoritesLiveData: LiveData<List<MealsData>> get() = _favoritesLiveData

    fun getSavedMeals() = viewModelScope.launch {
        val favoritesMeal = repository.getFavoriteMeals()
        _favoritesLiveData.value = favoritesMeal
    }

    fun saveFavoriteMeal(meal: MealsData) = viewModelScope.launch {
        repository.addToFavorite(meal)
    }

    fun deleteMeal(meal: MealsData) = viewModelScope.launch {
        repository.deleteFromFavorite(meal)
    }
}