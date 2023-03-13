package com.herdal.videogamehub.domain.use_case.game

import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import javax.inject.Inject

class IsGameInFavoriteUseCase @Inject constructor(
    private val favoriteGameRepository: FavoriteGameRepository
) {
    suspend fun execute(gameId: Int): Boolean = favoriteGameRepository.isGameFavorite(gameId)
}