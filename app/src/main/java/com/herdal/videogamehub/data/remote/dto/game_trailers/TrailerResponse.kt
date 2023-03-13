package com.herdal.videogamehub.data.remote.dto.game_trailers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrailerResponse(
    @Json(name = "results")
    val results: List<TrailerDto>
)