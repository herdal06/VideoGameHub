package com.herdal.videogamehub.data.remote.dto.game_screenshots

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScreenshotResponse(
    @Json(name = "results")
    val results: List<ScreenshotDto>
)