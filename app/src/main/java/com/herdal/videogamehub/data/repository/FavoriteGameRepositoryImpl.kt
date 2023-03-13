package com.herdal.videogamehub.data.repository

import com.herdal.videogamehub.data.local.dao.FavoriteGameDao
import com.herdal.videogamehub.data.local.entity.FavoriteGameEntity
import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteGameRepositoryImpl @Inject constructor(
    private val favoriteGameDao: FavoriteGameDao,
) : FavoriteGameRepository {
    override suspend fun isGameFavorite(id: Int): Boolean {
        return favoriteGameDao.isGameFavorite(id) != null
    }

    override suspend fun insert(favoriteGame: FavoriteGameEntity) {
        favoriteGameDao.insert(favoriteGame)
    }

    override fun getAll(): Flow<List<FavoriteGameEntity>> {
        return favoriteGameDao.getAll()
    }

    override suspend fun delete(favoriteGame: FavoriteGameEntity) {
        favoriteGameDao.delete(favoriteGame)
    }

    override suspend fun findGamesWithName(search: String): List<FavoriteGameEntity> {
        return favoriteGameDao.findGamesWithName(search)
    }
}