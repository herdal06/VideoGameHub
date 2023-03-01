package com.herdal.videogamehub.domain.ui_model

data class GenreUiModel(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val image_background: String
)