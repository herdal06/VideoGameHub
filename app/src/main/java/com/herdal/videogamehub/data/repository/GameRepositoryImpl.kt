package com.herdal.videogamehub.data.repository

import androidx.paging.*
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.local.entity.toGameUiModel
import com.herdal.videogamehub.data.paging.GameRemoteMediator
import com.herdal.videogamehub.data.remote.service.GameService
import com.herdal.videogamehub.domain.repository.GameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameService: GameService,
    private val database: AppDatabase
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
        }
    }
}