package com.herdal.videogamehub.domain.use_case.game

import androidx.paging.PagingData
import com.herdal.videogamehub.di.IoDispatcher
import com.herdal.videogamehub.domain.repository.GameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<GameUiModel>> {
        return gameRepository.searchGames(searchQuery = searchQuery)
    }
}