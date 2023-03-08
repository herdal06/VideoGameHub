package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.ScreenshotUiModel

@Entity(tableName = "screenshots")
data class ScreenshotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "image")
    val image: String? = null,
)

fun ScreenshotEntity.toScreenshotUiModel() = ScreenshotUiModel(
    id = this.id,
    image = this.image
)