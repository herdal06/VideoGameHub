package com.herdal.videogamehub.data.remote.dto.game_detail

import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDetailDto(
    @Json(name = "background_image_additional")
    val background_image_additional: String? = null,
    @Json(name = "background_image")
    val background_image: String? = null,
    @Json(name = "description_raw")
    val description_raw: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "rating")
    val rating: Double? = null,
    @Json(name = "released")
    val released: String? = null,
    @Json(name = "slug")
    val slug: String? = null,
)

fun GameDetailDto.toGameUiModel() = GameUiModel(
    background_image_additional = background_image_additional,
    description_raw = description_raw,
    id = id,
    name = name,
    rating = rating,
    released = released,
    slug = slug,
    background_image = background_image
)