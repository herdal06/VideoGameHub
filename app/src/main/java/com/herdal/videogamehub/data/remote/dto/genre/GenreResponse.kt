package com.herdal.videogamehub.data.remote.dto.genre

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(
    @Json(name = "results")
    val results: List<GenreDto>
)