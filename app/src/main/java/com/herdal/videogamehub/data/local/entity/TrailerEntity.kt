package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.utils.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.EntityNames.TRAILERS)
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