package com.herdal.videogamehub.data.remote.dto.genre

import com.herdal.videogamehub.data.local.entity.GenreEntity
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "image_background")
    val image_background: String? = null
)

fun GenreDto.toGenreEntity() = GenreEntity(
    id = id,
    name = name,
    slug = slug,
    image_background = image_background
)


fun GenreDto.toGenreUiModel() = GenreUiModel(
    id = id,
    name = name,
    slug = slug,
    image_background = image_background
)
