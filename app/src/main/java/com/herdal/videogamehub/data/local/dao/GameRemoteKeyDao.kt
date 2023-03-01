package com.herdal.videogamehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herdal.videogamehub.data.local.entity.GameRemoteKeyEntity

@Dao
interface GameRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<GameRemoteKeyEntity>)

    @Query("SELECT * FROM game_remote_keys WHERE id = :id")
    suspend fun remoteKeysCharacterId(id: Int): GameRemoteKeyEntity?

    @Query("DELETE FROM game_remote_keys")
    suspend fun deleteAll()
}