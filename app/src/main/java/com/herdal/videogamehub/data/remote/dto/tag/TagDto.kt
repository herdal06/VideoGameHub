package com.herdal.videogamehub.data.remote.dto.tag

import com.herdal.videogamehub.data.local.entity.TagEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TagDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_background")
    val image_background: String,
    @Json(name = "name")
    val name: String,
)

fun TagDto.toTagEntity() = TagEntity(
    id = this.id,
    image_background = this.image_background,
    name = this.name
)