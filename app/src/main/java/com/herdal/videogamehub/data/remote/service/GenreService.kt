package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig
import com.herdal.videogamehub.data.remote.dto.genre.GenreResponse
import com.herdal.videogamehub.data.remote.dto.genre.genre_detail.GenreDetailDto
import com.herdal.videogamehub.utils.constants.NetworkConstants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenreService {

    @GET(NetworkConstants.Endpoints.GENRES)
    suspend fun getGenres(
        @Query("key") key: String = BuildConfig.API_KEY,
    ): GenreResponse

    @GET(NetworkConstants.Endpoints.GENRE_BY_ID)
    suspend fun getGenreDetails(
        @Path("id") id: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GenreDetailDto
}