package com.example.myjourneyapp

import android.app.Application
import com.example.myjourneyapp.di.DaggerMealAppComponent

import com.example.myjourneyapp.di.MealAppComponent

class MyApp: Application() {
    val appComponent : MealAppComponent by lazy {
        DaggerMealAppComponent.factory().create(applicationContext)
    }
}