package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig.API_KEY
import com.herdal.videogamehub.data.remote.dto.game_trailers.TrailerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrailerService {

    @GET("games/{id}/movies")
    suspend fun getGameTrailers(
        @Path("id") gameId: Int,
        @Query("key") key: String = API_KEY,
    ): TrailerResponse
}