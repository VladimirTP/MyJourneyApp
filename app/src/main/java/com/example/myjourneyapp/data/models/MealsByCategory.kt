package com.example.myjourneyapp.data.models

import com.google.gson.annotations.SerializedName

data class MealsByCategory(
    @SerializedName("idMeal") val idMeal: String? = null,
    @SerializedName("strMeal") val strMeal: String? = null,
    @SerializedName("strMealThumb") val strMealThumb: String? = null
)