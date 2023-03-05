package com.herdal.videogamehub.data.local.dao

import androidx.room.*
import com.herdal.videogamehub.data.local.entity.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGameDao {

    @Query("SELECT * FROM favorite_games WHERE id=:id")
    suspend fun isGameFavorite(id: Int): FavoriteGameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteGame: FavoriteGameEntity)

    @Query("SELECT * FROM favorite_games")
    fun getAll(): Flow<List<FavoriteGameEntity>>

    @Delete
    suspend fun delete(favoriteGame: FavoriteGameEntity)

    @Query("SELECT * FROM favorite_games WHERE name LIKE '%' || :search || '%'")
    suspend fun findGamesWithName(search: String): List<FavoriteGameEntity>
}