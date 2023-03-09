package com.herdal.videogamehub.data.repository

import androidx.paging.*
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.local.entity.toGameUiModel
import com.herdal.videogamehub.data.paging.GameRemoteMediator
import com.herdal.videogamehub.data.remote.dto.game.toGameUiModel
import com.herdal.videogamehub.data.remote.dto.game_detail.toGameUiModel
import com.herdal.videogamehub.data.remote.paging_source.GamePagingSource
import com.herdal.videogamehub.data.remote.paging_source.GamesByGenrePagingSource
import com.herdal.videogamehub.data.remote.paging_source.GamesByTagPagingSource
import com.herdal.videogamehub.data.remote.service.GameService
import com.herdal.videogamehub.di.IoDispatcher
import com.herdal.videogamehub.domain.repository.GameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameService: GameService,
    private val database: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GameRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getGames(): Flow<PagingData<GameUiModel>> {
        val pagingSourceFactory = { database.gameDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = GameRemoteMediator(
                gameService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { gameEntityPagingData ->
            gameEntityPagingData.map { gameEntity -> gameEntity.toGameUiModel() }
        }.cachedIn(CoroutineScope(ioDispatcher))
    }

    override fun searchGames(searchQuery: String): Flow<PagingData<GameUiModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GamePagingSource(
                    gameService = gameService,
                    searchQuery = searchQuery
                )
            }
        ).flow.map { gameDtoPagingData ->
            gameDtoPagingData.map { gameDto ->
                gameDto.toGameUiModel()
            }
        }.cachedIn(CoroutineScope(ioDispatcher))

    override suspend fun getGameDetails(gameId: Int): GameUiModel =
        withContext(ioDispatcher) {
            gameService.getGameDetails(id = gameId).toGameUiModel()
        }

    override fun getGamesByGenre(genreId: Int): Flow<PagingData<GameUiModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GamesByGenrePagingSource(
                    gameService = gameService,
                    genreId = genreId
                )
            }
        ).flow.map { gameDtoPagingData ->
            gameDtoPagingData.map { gameDto -> gameDto.toGameUiModel() }
        }.cachedIn(CoroutineScope(ioDispatcher))

    override fun getGamesByTag(tagId: Int): Flow<PagingData<GameUiModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GamesByTagPagingSource(
                    gameService = gameService,
                    tagId = tagId
                )
            }
        ).flow.map { gameDtoPagingData ->
            gameDtoPagingData.map { gameDto -> gameDto.toGameUiModel() }
        }.cachedIn(CoroutineScope(ioDispatcher))
}