package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig
import com.herdal.videogamehub.data.remote.dto.tag.TagResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TagService {

    @GET("tags")
    suspend fun getTags(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
    ): TagResponse
}