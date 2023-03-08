package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.utils.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.EntityNames.GENRES)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "slug")
    val slug: String,
    @ColumnInfo(name = "image_background")
    val image_background: String? = null,
    @ColumnInfo(name = "description")
    val description: String? = null
)

fun GenreEntity.toGenreUiModel() = GenreUiModel(
    id = id,
    name = name,
    slug = slug,
    image_background = image_background,
    description = description
)