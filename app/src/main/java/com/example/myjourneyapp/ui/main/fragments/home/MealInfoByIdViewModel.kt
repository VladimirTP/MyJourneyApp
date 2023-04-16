package com.example.myjourneyapp.ui.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjourneyapp.R
import com.example.myjourneyapp.domain.MealsRepository
import com.example.myjourneyapp.domain.models.MealsData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

class MealInfoByIdViewModel @Inject constructor(private val repository: MealsRepository) : ViewModel() {

    private val _liveDataById = MutableLiveData<List<MealsData>>()
    val liveDataById: LiveData<List<MealsData>> get() = _liveDataById

    private val _errorLiveData = MutableLiveData<Int>()
    val errorLiveData: LiveData<Int> get() = _errorLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is SocketTimeoutException -> _errorLiveData.value = R.string.connection_error
            else -> _errorLiveData.value = R.string.unknown_error
        }
    }

    fun getMealInfoById(id: String) {
        _loadingLiveData.value = true
        viewModelScope.launch(exceptionHandler) {
            val mealsListById = repository.getListOfMealsInfoById(id)
            _liveDataById.value = mealsListById
            _loadingLiveData.value = false
        }
    }
}