package com.herdal.videogamehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.herdal.videogamehub.data.local.dao.FavoriteGameDao
import com.herdal.videogamehub.data.local.dao.GameDao
import com.herdal.videogamehub.data.local.dao.GameRemoteKeyDao
import com.herdal.videogamehub.data.local.dao.GenreDao
import com.herdal.videogamehub.data.local.entity.FavoriteGameEntity
import com.herdal.videogamehub.data.local.entity.GameEntity
import com.herdal.videogamehub.data.local.entity.GameRemoteKeyEntity
import com.herdal.videogamehub.data.local.entity.GenreEntity

@Database(
    entities = [
        GameEntity::class,
        GameRemoteKeyEntity::class,
        GenreEntity::class,
        FavoriteGameEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun gameRemoteKeyDao(): GameRemoteKeyDao
    abstract fun genreDao(): GenreDao
    abstract fun favoriteGameDao(): FavoriteGameDao
}