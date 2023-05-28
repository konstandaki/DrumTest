package com.konstandaki.drumtest.network

import com.konstandaki.drumtest.model.Sport
import retrofit2.http.GET

interface SportsApiService {
    @GET("demo")
    suspend fun getSports(): List<Sport>
}