package com.herdal.videogamehub.data.remote.dto.genre.genre_detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDetailDto(
    @Json(name = "description")
    val description: String,
    @Json(name = "games_count")
    val games_count: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_background")
    val image_background: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String
)