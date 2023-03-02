package com.herdal.videogamehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.herdal.videogamehub.data.local.dao.GameDao
import com.herdal.videogamehub.data.local.dao.GameRemoteKeyDao
import com.herdal.videogamehub.data.local.entity.GameEntity
import com.herdal.videogamehub.data.local.entity.GameRemoteKeyEntity

@Database(
    entities = [
        GameEntity::class,
        GameRemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun gameRemoteKeyDao(): GameRemoteKeyDao
}