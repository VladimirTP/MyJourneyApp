package com.example.myjourneyapp.di

import com.example.myjourneyapp.data.MealsRepositoryImpl
import com.example.myjourneyapp.domain.MealsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun getRepository(impl: MealsRepositoryImpl): MealsRepository
}