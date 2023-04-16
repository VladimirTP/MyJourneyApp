package com.example.myjourneyapp.data.models

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal") val idMeal: String? = null,
    @SerializedName("strMealThumb") val imageUrl: String? = null,
    @SerializedName("strMeal") val mealName: String? = null,
    @SerializedName("strInstructions") val mealDetails: String? = null,
    @SerializedName("strCategory") val mealCategory: String? = null,
    @SerializedName("strArea") val mealArea: String? = null
)
