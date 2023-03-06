package com.herdal.videogamehub.domain.use_case

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.repository.GameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesByGenreUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke(genreId: Int): Flow<PagingData<GameUiModel>> {
        return gameRepository.getGamesByGenre(genreId = genreId)
    }
}