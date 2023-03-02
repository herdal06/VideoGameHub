package com.herdal.videogamehub.data.remote.dto.genre

import com.herdal.videogamehub.data.local.entity.GenreEntity
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "idCategory")
    val id: Int,
    @Json(name = "idCategory")
    val name: String,
    @Json(name = "idCategory")
    val slug: String,
    @Json(name = "idCategory")
    val games_count: Int,
    @Json(name = "idCategory")
    val image_background: String? = null
)

fun GenreDto.toGenreEntity() = image_background?.let {
    GenreEntity(
        id = id,
        name = name,
        slug = slug,
        games_count = games_count,
        image_background = it
    )
}

fun GenreDto.toGenreUiModel() = image_background?.let {
    GenreUiModel(
        id = id,
        name = name,
        slug = slug,
        games_count = games_count,
        image_background = it
    )
}