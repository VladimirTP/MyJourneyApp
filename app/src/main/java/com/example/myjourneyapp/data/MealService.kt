package com.example.myjourneyapp.data

import com.example.myjourneyapp.data.models.CategoryList
import com.example.myjourneyapp.data.models.MealsByCategoryList
import com.example.myjourneyapp.data.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealInfoById(@Query("i") id: String): Call<MealList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsDetailsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>
}