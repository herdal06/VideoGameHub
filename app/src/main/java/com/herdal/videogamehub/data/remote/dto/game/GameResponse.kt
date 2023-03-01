package com.herdal.videogamehub.data.remote.dto.game

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameResponse(
    @Json(name = "results")
    val results: List<GameDto>,
)