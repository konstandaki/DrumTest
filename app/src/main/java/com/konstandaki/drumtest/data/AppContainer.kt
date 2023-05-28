package com.konstandaki.drumtest.data

import com.konstandaki.drumtest.network.SportsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val sportsRepository: SportsRepository
}

class DefaultAppContainer : AppContainer {

    private val BASE_URL = "https://62a054d8202ceef7086aed64.mockapi.io/api/v1/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: SportsApiService by lazy { retrofit.create(SportsApiService::class.java) }

    override val sportsRepository: SportsRepository by lazy {
        NetworkSportsRepository(retrofitService)
    }
}