package com.example.digimonexplorer.data.remote

import com.example.digimonexplorer.data.model.DigimonDetailDto
import com.example.digimonexplorer.data.model.DigimonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigiApiService {
    @GET("digimon")
    suspend fun getDigimonList(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = 30
    ): DigimonListResponse

    @GET("digimon/{id}")
    suspend fun getDigimonDetail(@Path("id") id: Int): DigimonDetailDto
}
