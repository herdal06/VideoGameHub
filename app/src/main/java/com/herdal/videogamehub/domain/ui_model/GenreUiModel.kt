package com.herdal.videogamehub.domain.ui_model

data class GenreUiModel(
    val id: Int,
    val name: String,
    val slug: String,
    val image_background: String? = null,
    val description: String? = null,
)