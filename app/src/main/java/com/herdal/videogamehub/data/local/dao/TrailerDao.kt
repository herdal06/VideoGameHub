package com.herdal.videogamehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herdal.videogamehub.data.local.entity.TrailerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrailerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trailers: List<TrailerEntity>)

    @Query("SELECT * FROM trailers")
    fun getAll(): Flow<List<TrailerEntity>>

    @Query("DELETE FROM trailers")
    suspend fun deleteAll()
}