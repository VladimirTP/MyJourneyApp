package com.example.myjourneyapp.data.database

import androidx.room.*

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meals: MealsEntity)

    @Query("SELECT * FROM mealInformation")
    suspend fun getAll(): List<MealsEntity>

    @Delete
    suspend fun delete(meals: MealsEntity)
}