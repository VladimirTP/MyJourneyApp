package com.example.myjourneyapp.data

import com.example.myjourneyapp.data.database.MealsAppDataBase
import com.example.myjourneyapp.data.mappers.MealsMapper
import com.example.myjourneyapp.domain.MealsRepository
import com.example.myjourneyapp.domain.models.MealsCategoryData
import com.example.myjourneyapp.domain.models.MealsPopularData
import com.example.myjourneyapp.domain.models.MealsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mapper: MealsMapper,
    private val service: MealService,
    private val dataBase: MealsAppDataBase
) : MealsRepository {

    override suspend fun getListOfMealsData(): List<MealsData> {
        return withContext(Dispatchers.IO) {
            service.getRandomMeal().execute().body()?.meals?.map { mapper(it) }
                ?: throw Exception()
        }
    }

    override suspend fun getListOfMealsInfoById(id: String): List<MealsData> {
        return withContext(Dispatchers.IO) {
            service.getMealInfoById(id).execute().body()?.meals?.map { mapper(it) }
                ?: throw Exception()
        }
    }

    override suspend fun getListOfMealsPopularData(): List<MealsPopularData> {
        return withContext(Dispatchers.IO) {
            service.getMealsDetailsByCategory("Seafood").execute().body()?.meals?.map { mapper.mealsByCategoryToMealsPopularData(it) }
                ?: throw Exception()
        }
    }

    override suspend fun getListOfMealsCategoryData(): List<MealsCategoryData> {
        return withContext(Dispatchers.IO) {
            service.getCategories().execute().body()?.categories?.map { mapper.categoryToMealsCategoryData(it) }
                ?: throw Exception()
        }
    }

    override suspend fun getListOfMealsCategory(categoryName: String): List<MealsPopularData> {
        return withContext(Dispatchers.IO) {
            service.getMealsDetailsByCategory(categoryName).execute().body()?.meals?.map { mapper.mealsByCategoryToMealsPopularData(it) }
                ?: throw Exception()
        }
    }

    override suspend fun getFavoriteMeals (): List<MealsData> {
        val entityList = dataBase.getDao().getAll()
        return withContext(Dispatchers.IO) {mapper.toDataList(entityList)}
    }

    override suspend fun addToFavorite (meal: MealsData) = withContext(Dispatchers.IO) {dataBase.getDao().insertMeal(mapper.mealsDataToEntity(meal))}

    override suspend fun deleteFromFavorite (meal: MealsData) = withContext(Dispatchers.IO) {dataBase.getDao().delete(mapper.mealsDataToEntity(meal))}
}