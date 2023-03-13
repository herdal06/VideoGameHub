package com.herdal.videogamehub.data.remote.dto.game_trailers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "max")
    val max: String? = null
)