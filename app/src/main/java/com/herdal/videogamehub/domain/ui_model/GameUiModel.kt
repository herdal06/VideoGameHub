package com.herdal.videogamehub.domain.ui_model

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
)