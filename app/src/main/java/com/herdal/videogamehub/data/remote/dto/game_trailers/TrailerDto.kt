package com.herdal.videogamehub.data.remote.dto.game_trailers

import com.herdal.videogamehub.data.local.entity.TrailerEntity
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrailerDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "data")
    val data: Data? = null,
    @Json(name = "preview")
    val preview: String? = null
)

fun TrailerDto.toTrailerEntity() = TrailerEntity(
    id = this.id,
    name = this.name,
    preview = this.preview,
    data = this.data?.max
)

fun TrailerDto.toTrailerUiModel() = TrailerUiModel(
    id = this.id,
    name = this.name,
    preview = this.preview,
    data = this.data?.max
)