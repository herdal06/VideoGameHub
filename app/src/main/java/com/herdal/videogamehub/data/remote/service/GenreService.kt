package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig
import com.herdal.videogamehub.data.remote.dto.genre.GenreResponse
import com.herdal.videogamehub.data.remote.dto.genre.genre_detail.GenreDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenreService {

    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String = BuildConfig.API_KEY,
    ): GenreResponse

    @GET("genres/{id}")
    suspend fun getGenreDetails(
        @Path("id") id: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GenreDetailDto
}