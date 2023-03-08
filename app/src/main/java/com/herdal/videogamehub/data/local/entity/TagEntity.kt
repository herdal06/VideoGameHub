package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import com.herdal.videogamehub.utils.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.EntityNames.TAGS)
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "image_background")
    val image_background: String,
    @ColumnInfo(name = "name")
    val name: String,
)

fun TagEntity.toTagUiModel() = TagUiModel(
    id = this.id,
    image_background = this.image_background,
    name = this.name
)