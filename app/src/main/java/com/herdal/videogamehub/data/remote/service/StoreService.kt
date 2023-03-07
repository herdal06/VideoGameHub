package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig.API_KEY
import com.herdal.videogamehub.data.remote.dto.store.StoreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreService {

    @GET("stores")
    suspend fun getStores(
        @Query("key") key: String = API_KEY,
    ): StoreResponse
}