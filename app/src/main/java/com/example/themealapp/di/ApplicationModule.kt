package com.example.themealapp.di

import com.example.themealapp.api.APIService
import com.example.themealapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) :APIService{
        return retrofit.create(APIService::class.java)
    }
}