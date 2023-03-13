package com.herdal.videogamehub.data.remote.service

import com.herdal.videogamehub.BuildConfig
import com.herdal.videogamehub.data.remote.dto.game_screenshots.ScreenshotResponse
import com.herdal.videogamehub.utils.constants.NetworkConstants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScreenshotService {

    @GET(NetworkConstants.Endpoints.SCREENSHOTS)
    suspend fun getGameScreenshots(
        @Path("id") gameId: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): ScreenshotResponse
}