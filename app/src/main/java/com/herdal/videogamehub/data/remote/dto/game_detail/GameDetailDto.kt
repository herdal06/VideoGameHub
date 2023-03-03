package com.herdal.videogamehub.data.remote.dto.game_detail

import com.herdal.videogamehub.domain.ui_model.GameUiModel

data class GameDetailDto(
    val background_image_additional: String? = null,
    val description_raw: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val rating: Int? = null,
    val released: String? = null,
    val slug: String? = null,
)

fun GameDetailDto.toGameUiModel() = GameUiModel(
    background_image_additional = background_image_additional,
    description_raw =description_raw,
    id = id,
    name = name,
    rating = rating,
    released = released,
    slug = slug
)