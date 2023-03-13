package com.herdal.videogamehub.data.repository

import androidx.paging.*
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.local.entity.toTagUiModel
import com.herdal.videogamehub.data.paging.TagRemoteMediator
import com.herdal.videogamehub.data.remote.service.TagService
import com.herdal.videogamehub.di.IoDispatcher
import com.herdal.videogamehub.domain.repository.TagRepository
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagService: TagService,
    private val database: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TagRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getTags(): Flow<PagingData<TagUiModel>> {
        val pagingSourceFactory = { database.tagDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = TagRemoteMediator(
                tagService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                it.toTagUiModel()
            }
        }.cachedIn(CoroutineScope(ioDispatcher))
    }
}