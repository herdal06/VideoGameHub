package com.herdal.videogamehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herdal.videogamehub.data.local.entity.ScreenshotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenshotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<ScreenshotEntity>)

    @Query("SELECT * FROM screenshots")
    fun getAll(): Flow<List<ScreenshotEntity>>

    @Query("DELETE FROM screenshots")
    suspend fun deleteAll()
}