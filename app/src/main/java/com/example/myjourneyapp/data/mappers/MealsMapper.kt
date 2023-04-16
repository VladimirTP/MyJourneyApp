package com.example.myjourneyapp.data.mappers

import com.example.myjourneyapp.data.database.MealsEntity
import com.example.myjourneyapp.data.models.Category
import com.example.myjourneyapp.data.models.MealsByCategory
import com.example.myjourneyapp.data.models.Meal
import com.example.myjourneyapp.domain.models.MealsCategoryData
import com.example.myjourneyapp.domain.models.MealsPopularData
import com.example.myjourneyapp.domain.models.MealsData
import javax.inject.Inject

class MealsMapper @Inject constructor() {

    operator fun invoke(response: Meal): MealsData = with(response) {
        return MealsData(
            idMeal = idMeal.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            mealName = mealName.orEmpty(),
            mealDetails = mealDetails.orEmpty(),
            mealCategory = mealCategory.orEmpty(),
            mealArea = mealArea.orEmpty()
        )
    }

    fun mealsByCategoryToMealsPopularData (popularCategory: MealsByCategory) = with(popularCategory) {
        MealsPopularData(
            idMeal = idMeal.orEmpty(),
            mealName = strMeal.orEmpty(),
            imageUrl = strMealThumb.orEmpty()
        )
    }

    fun categoryToMealsCategoryData (category: Category) = with(category) {
        MealsCategoryData(
            idCategory = idCategory.orEmpty(),
            strCategory = strCategory.orEmpty(),
            strCategoryDescription = strCategoryDescription.orEmpty(),
            strCategoryThumb = strCategoryThumb.orEmpty()
        )
    }

    fun mealsDataToEntity(mealsData: MealsData): MealsEntity = with(mealsData) {
        return MealsEntity(
            idMeal = idMeal,
            imageUrl = imageUrl,
            mealName = mealName,
            mealDetails = mealDetails,
            mealCategory = mealCategory,
            mealArea = mealArea
        )
    }

    fun entityToMealsData(entity: MealsEntity): MealsData = with(entity) {
        return MealsData(
            idMeal = idMeal,
            imageUrl = imageUrl,
            mealName = mealName,
            mealDetails = mealDetails,
            mealCategory = mealCategory,
            mealArea = mealArea
        )
    }

    fun toDataList(entityList: List<MealsEntity>): List<MealsData> {
        return entityList.map { entityToMealsData(it) }
    }
}