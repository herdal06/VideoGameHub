package com.herdal.videogamehub.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreResponse(
    @Json(name = "results")
    val results: List<StoreDto>
)