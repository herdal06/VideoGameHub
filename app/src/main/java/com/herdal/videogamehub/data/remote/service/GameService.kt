package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig.API_KEY
import com.herdal.videogamehub.data.remote.dto.game.GameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {

    @GET("games")
    suspend fun getGames(
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int
    ): GameResponse
}