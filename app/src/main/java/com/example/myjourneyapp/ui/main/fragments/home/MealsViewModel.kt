package com.example.myjourneyapp.ui.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjourneyapp.R
import com.example.myjourneyapp.domain.MealsRepository
import com.example.myjourneyapp.domain.models.MealsCategoryData
import com.example.myjourneyapp.domain.models.MealsPopularData
import com.example.myjourneyapp.domain.models.MealsData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

class MealsViewModel @Inject constructor(
    private val repository: MealsRepository
) : ViewModel(){

    private val _liveData = MutableLiveData<List<MealsData>>()
    val liveData: LiveData<List<MealsData>> get() = _liveData

    private val _popularMealsLiveData = MutableLiveData<List<MealsPopularData>>()
    val popularMealsLiveData: LiveData<List<MealsPopularData>> get() = _popularMealsLiveData

    private val _categoriesLiveData = MutableLiveData<List<MealsCategoryData>>()
    val categoriesLiveData: LiveData<List<MealsCategoryData>> get() = _categoriesLiveData

    private val _mealsByCategoryLiveData = MutableLiveData<List<MealsPopularData>>()
    val mealsByCategoryLiveData: LiveData<List<MealsPopularData>> get() = _mealsByCategoryLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> get() = _errorLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is SocketTimeoutException -> _errorLiveData.value = R.string.connection_error
            else -> _errorLiveData.value = R.string.unknown_error
        }
    }

    fun getRandomMeal() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            val mealsList = repository.getListOfMealsData()
            _liveData.value = mealsList
            _loadingLiveData.value = false
        }
    }

    fun getPopularItems() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            val popularMealsList = repository.getListOfMealsPopularData()
            _popularMealsLiveData.value = popularMealsList
            _loadingLiveData.value = false
        }
    }

    fun getCategories() {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            val categoryMealsList = repository.getListOfMealsCategoryData()
            _categoriesLiveData.value = categoryMealsList
            _loadingLiveData.value = false
        }
    }

    fun getMealByCategory(categoryName: String) {
        viewModelScope.launch(exceptionHandler) {
            val mealsCategory = repository.getListOfMealsCategory(categoryName)
            _mealsByCategoryLiveData.value = mealsCategory
        }
    }
}