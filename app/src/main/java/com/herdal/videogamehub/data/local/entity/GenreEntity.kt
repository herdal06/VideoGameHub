package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.GenreUiModel

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "idCategory")
    val name: String,
    @ColumnInfo(name = "idCategory")
    val slug: String,
    @ColumnInfo(name = "idCategory")
    val games_count: Int,
    @ColumnInfo(name = "idCategory")
    val image_background: String
)

fun GenreEntity.toGenreUiModel() = GenreUiModel(
    id = id,
    name = name,
    slug = slug,
    games_count = games_count,
    image_background = image_background
)