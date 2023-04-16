package com.example.myjourneyapp.data.models

import com.google.gson.annotations.SerializedName

data class CategoryList(
    @SerializedName("categories") val categories: List<Category>? = null
)