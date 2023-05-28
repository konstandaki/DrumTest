package com.konstandaki.drumtest.data

import com.konstandaki.drumtest.model.Sport
import com.konstandaki.drumtest.network.SportsApiService

interface SportsRepository {
    suspend fun getSports(): List<Sport>
}

class NetworkSportsRepository(private val sportsApiService: SportsApiService) : SportsRepository {
    override suspend fun getSports(): List<Sport> = sportsApiService.getSports()
}