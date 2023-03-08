package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig
import com.herdal.videogamehub.data.remote.dto.game_screenshots.ScreenshotResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScreenshotService {

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshots(
        @Path("id") gameId: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): ScreenshotResponse
}