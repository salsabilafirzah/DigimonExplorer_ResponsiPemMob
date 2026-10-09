package com.example.digimonexplorer.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: DigiApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://digi-api.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DigiApiService::class.java)
    }
}
