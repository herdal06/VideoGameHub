package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig.API_KEY
import com.herdal.videogamehub.data.remote.dto.game.GameResponse
import com.herdal.videogamehub.data.remote.dto.game_detail.GameDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @GET("games")
    suspend fun getTopRatedPcGames(
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int,
        @Query("metacritic") metacritic: String = "90,100",
        @Query("platform") platform: Int = 4 // PC's id is 4
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

    @GET("games")
    suspend fun getGamesByGenre(
        @Query("key") key: String = API_KEY,
        @Query("genres") genreId: Int,
        @Query("page") page: Int,
    ): GameResponse
}