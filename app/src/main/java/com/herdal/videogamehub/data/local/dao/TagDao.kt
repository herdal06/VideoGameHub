package com.herdal.videogamehub.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.herdal.videogamehub.data.local.entity.TagEntity

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tags: List<TagEntity>)

    @Query("SELECT * FROM tags")
    fun getAll(): PagingSource<Int, TagEntity>

    @Query("DELETE FROM tags")
    suspend fun deleteAll()
}