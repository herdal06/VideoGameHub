package com.herdal.videogamehub.domain.repository

import com.herdal.videogamehub.data.local.entity.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteGameRepository {
    suspend fun isGameFavorite(id: Int): Boolean
    suspend fun insert(favoriteGame: FavoriteGameEntity)
    fun getAll(): Flow<List<FavoriteGameEntity>>
    suspend fun delete(favoriteGame: FavoriteGameEntity)
    suspend fun findGamesWithName(search: String): List<FavoriteGameEntity>
}