package com.herdal.videogamehub.data.remote.dto

import com.herdal.videogamehub.data.local.entity.StoreEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
)

fun StoreDto.toStoreEntity() =
    StoreEntity(
        id = this.id,
        name = this.name
    )