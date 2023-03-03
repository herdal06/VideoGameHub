package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig.API_KEY
import com.herdal.videogamehub.data.remote.dto.game.GameResponse
import com.herdal.videogamehub.data.remote.dto.game_detail.GameDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @GET("games")
    suspend fun getGames(
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int
    ): GameResponse

    @GET("games")
    suspend fun searchGames(
        @Query("key") key: String = API_KEY,
        @Query("search") searchQuery: String,
        @Query("page") page: Int,
    ): GameResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
        @Query("key") key: String = API_KEY
    ): GameDetailDto
}