package com.herdal.videogamehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.herdal.videogamehub.data.local.dao.*
import com.herdal.videogamehub.data.local.entity.*

@Database(
    entities = [
        GameEntity::class,
        GameRemoteKeyEntity::class,
        GenreEntity::class,
        FavoriteGameEntity::class,
        StoreEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun gameRemoteKeyDao(): GameRemoteKeyDao
    abstract fun genreDao(): GenreDao
    abstract fun favoriteGameDao(): FavoriteGameDao
    abstract fun storeDao(): StoreDao
}