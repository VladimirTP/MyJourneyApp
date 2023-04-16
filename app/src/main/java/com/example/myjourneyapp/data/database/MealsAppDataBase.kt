package com.example.myjourneyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MealsEntity::class], version = 1)
abstract class MealsAppDataBase: RoomDatabase() {

    abstract fun getDao(): MealsDao
}