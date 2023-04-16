package com.example.myjourneyapp.di

import android.content.Context
import androidx.room.Room
import com.example.myjourneyapp.data.database.MealsAppDataBase
import com.example.myjourneyapp.data.database.MealsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun createDataBase(context: Context): MealsAppDataBase {
        return Room.databaseBuilder(
            context,
            MealsAppDataBase::class.java,
            "mealDataBase"
        ).build()
    }
    @Singleton
    @Provides
    fun getMealsDao(database: MealsAppDataBase): MealsDao = database.getDao()
}