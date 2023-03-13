package com.herdal.videogamehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herdal.videogamehub.data.local.entity.TagRemoteKeyEntity

@Dao
interface TagRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TagRemoteKeyEntity>)

    @Query("SELECT * FROM tag_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): TagRemoteKeyEntity?

    @Query("DELETE FROM tag_remote_keys")
    suspend fun deleteAll()
}