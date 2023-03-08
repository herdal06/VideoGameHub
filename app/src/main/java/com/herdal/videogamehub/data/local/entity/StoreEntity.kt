package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.StoreUiModel
import com.herdal.videogamehub.utils.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.EntityNames.STORES)
data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
)

fun StoreEntity.toStoreUiModel() =
    StoreUiModel(
        id = this.id,
        name = this.name
    )