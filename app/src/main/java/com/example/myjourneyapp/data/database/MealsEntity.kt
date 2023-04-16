package com.example.myjourneyapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealInformation")
data class MealsEntity(
    @PrimaryKey
    val idMeal: String,
    @ColumnInfo val imageUrl: String,
    @ColumnInfo val mealName: String,
    @ColumnInfo val mealDetails: String,
    @ColumnInfo val mealCategory: String,
    @ColumnInfo val mealArea: String
)
