package com.example.themealapp.data.remote

import com.example.themealapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    fun builder() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
//        .client(provideOkHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()!!
    private fun provideLoggingInterceptor():Interceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private fun provideOkHttp() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(provideLoggingInterceptor())
            .build();
    }
}