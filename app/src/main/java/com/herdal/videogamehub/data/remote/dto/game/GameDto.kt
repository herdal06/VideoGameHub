package com.herdal.videogamehub.data.remote.dto.game

import com.herdal.videogamehub.data.local.entity.GameEntity
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "background_image")
    val background_image: String? = null,
    @Json(name = "metacritic")
    val metacritic: Int? = null,
    @Json(name = "released")
    val released: String? = null,
)

fun GameDto.toGameEntity() = GameEntity(
    id = id,
    name = name,
    background_image = background_image!!,
    metacritic = metacritic,
    released = released
)

fun GameDto.toGameUiModel() = GameUiModel(
    id = id,
    name = name,
    background_image = background_image,
    metacritic = metacritic,
    released = released
)