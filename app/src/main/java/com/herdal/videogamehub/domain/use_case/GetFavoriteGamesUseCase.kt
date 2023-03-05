package com.herdal.videogamehub.domain.use_case

import com.herdal.videogamehub.data.local.entity.toGameUiModel
import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val gameRepository: FavoriteGameRepository,
) {
    operator fun invoke(): Flow<List<GameUiModel>> {
        return gameRepository.getAll().map { entityList ->
            entityList.map { gameEntity -> gameEntity.toGameUiModel() }
        }
    }
}