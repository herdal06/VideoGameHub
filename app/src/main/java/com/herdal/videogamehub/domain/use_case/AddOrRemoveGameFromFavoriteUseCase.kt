package com.herdal.videogamehub.domain.use_case

import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.toFavoriteGameEntity
import timber.log.Timber
import javax.inject.Inject

class AddOrRemoveGameFromFavoriteUseCase @Inject constructor(
    private val favoriteGameRepository: FavoriteGameRepository,
    private val isGameInFavoriteUseCase: IsGameInFavoriteUseCase
) {
    suspend operator fun invoke(game: GameUiModel) {
        if (game.id?.let { isGameInFavoriteUseCase.execute(it) } == true) {
            game.isFavorite = false
            favoriteGameRepository.delete(game.toFavoriteGameEntity())
            Timber.d("should be false: ${game.isFavorite}")
        } else {
            game.isFavorite = true
            favoriteGameRepository.insert(game.toFavoriteGameEntity())
            Timber.d("should be true: ${game.isFavorite}")
        }
    }
}