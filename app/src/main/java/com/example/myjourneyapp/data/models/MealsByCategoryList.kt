package com.example.myjourneyapp.data.models

import com.google.gson.annotations.SerializedName

data class MealsByCategoryList(
    @SerializedName("meals") val meals: List<MealsByCategory>? = null
)