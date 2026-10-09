package com.example.digimonexplorer.data.repository

import com.example.digimonexplorer.data.model.Digimon
import com.example.digimonexplorer.data.model.toBasicDigimon
import com.example.digimonexplorer.data.model.toDigimon
import com.example.digimonexplorer.data.remote.DigiApiService
import com.example.digimonexplorer.data.remote.RetrofitInstance
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class DigimonRepository(
    private val api: DigiApiService = RetrofitInstance.api
) {
    // Ambil list, lalu ambil detail tiap item secara paralel
    suspend fun getDigimonList(): List<Digimon> = coroutineScope {
        api.getDigimonList().content.map { item ->
            async {
                runCatching { api.getDigimonDetail(item.id).toDigimon() }
                    .getOrElse { item.toBasicDigimon() }
            }
        }.awaitAll()
    }

    suspend fun getDigimonDetail(id: Int): Digimon =
        api.getDigimonDetail(id).toDigimon()
}
