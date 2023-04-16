package com.example.myjourneyapp.domain

import com.example.myjourneyapp.domain.models.MealsCategoryData
import com.example.myjourneyapp.domain.models.MealsPopularData
import com.example.myjourneyapp.domain.models.MealsData

interface MealsRepository {

    suspend fun getListOfMealsData(): List<MealsData>

    suspend fun getListOfMealsInfoById(id: String): List<MealsData>

    suspend fun getListOfMealsPopularData(): List<MealsPopularData>

    suspend fun getListOfMealsCategoryData(): List<MealsCategoryData>

    suspend fun getListOfMealsCategory(categoryName: String): List<MealsPopularData>

    suspend fun getFavoriteMeals (): List<MealsData>

    suspend fun addToFavorite (meal: MealsData)

    suspend fun deleteFromFavorite (meal: MealsData)
}