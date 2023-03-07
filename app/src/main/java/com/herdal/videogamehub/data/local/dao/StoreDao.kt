package com.herdal.videogamehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herdal.videogamehub.data.local.entity.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<StoreEntity>)

    @Query("SELECT * FROM stores")
    fun getAll(): Flow<List<StoreEntity>>

    @Query("DELETE FROM stores")
    suspend fun deleteAll()
}