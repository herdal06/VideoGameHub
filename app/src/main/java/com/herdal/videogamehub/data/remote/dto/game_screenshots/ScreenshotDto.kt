package com.herdal.videogamehub.data.remote.dto.game_screenshots

import com.herdal.videogamehub.data.local.entity.ScreenshotEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScreenshotDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String? = null,
)

fun ScreenshotDto.toScreenshotEntity() = ScreenshotEntity(
    id = this.id,
    image = this.image
)