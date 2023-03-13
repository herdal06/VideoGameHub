package com.herdal.videogamehub.domain.ui_model

import com.herdal.videogamehub.data.local.entity.FavoriteGameEntity

data class GameUiModel(
    val id: Int? = null,
    val name: String? = null,
    val background_image: String? = null,
    val metacritic: Int? = null,
    val released: String? = null,
    val background_image_additional: String? = null,
    val description_raw: String? = null,
    val rating: Double? = null,
    val slug: String? = null,
    var isFavorite: Boolean? = false
)

fun GameUiModel.toFavoriteGameEntity() = FavoriteGameEntity(
    id = id,
    name = name,
    background_image = background_image,
    metacritic = metacritic,
    released = released,
    isFavorite = isFavorite,
)