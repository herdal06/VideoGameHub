package com.herdal.videogamehub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.herdal.videogamehub.domain.ui_model.GameUiModel

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "background_image")
    val background_image: String,
    @ColumnInfo(name = "metacritic")
    val metacritic: Int,
    @ColumnInfo(name = "released")
    val released: String,
)

fun GameEntity.toGameUiModel() = background_image?.let {
    GameUiModel(
        id = id,
        name = name,
        background_image = it,
        metacritic = metacritic,
        released = released
    )
}