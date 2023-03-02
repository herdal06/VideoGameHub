package com.herdal.videogamehub.domain.ui_model

data class GameUiModel(
    val id: Int,
    val name: String,
    val background_image: String? = null,
    val metacritic: Int? = null,
    val released: String,
)