package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig.API_KEY
import com.herdal.videogamehub.data.remote.dto.store.StoreResponse
import com.herdal.videogamehub.utils.constants.NetworkConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreService {

    @GET(NetworkConstants.Endpoints.STORES)
    suspend fun getStores(
        @Query("key") key: String = API_KEY,
    ): StoreResponse
}