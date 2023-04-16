package com.example.myjourneyapp.data.models

import com.google.gson.annotations.SerializedName

data class MealList(
    @SerializedName("meals") val meals: List<Meal>? = null
)
