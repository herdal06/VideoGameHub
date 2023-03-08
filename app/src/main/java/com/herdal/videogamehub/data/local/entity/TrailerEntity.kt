package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel

@Entity(tableName = "trailers")
data class TrailerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "preview")
    val preview: String? = null,
    @ColumnInfo(name = "data")
    val data: String? = null
)

fun TrailerEntity.toTrailerUiModel() = TrailerUiModel(
    id = this.id,
    name = this.name,
    preview = this.preview,
    data = this.data
)