package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.utils.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.EntityNames.GAME_REMOTE_KEYS)
data class GameRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "mext_key")
    val nextKey: Int?
)