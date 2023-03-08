package com.herdal.videogamehub.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.local.entity.TagEntity
import com.herdal.videogamehub.data.local.entity.TagRemoteKeyEntity
import com.herdal.videogamehub.data.remote.dto.tag.toTagEntity
import com.herdal.videogamehub.data.remote.service.TagService
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class TagRemoteMediator(
    private val tagService: TagService,
    private val database: AppDatabase
) : RemoteMediator<Int, TagEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TagEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
            val response = tagService.getTags(page = page)
            val isEndOfList = response.toString()
                .contains("error") || response.results.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.tagDao().deleteAll()
                    database.tagRemoteKeyDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    TagRemoteKeyEntity(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.tagRemoteKeyDao().insertAll(keys)
                response.results.map { it.toTagEntity() }.let { database.tagDao().insertAll(it) }
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, TagEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
            else -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TagEntity>
    ): TagRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.tagRemoteKeyDao().getRemoteKeys(id)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, TagEntity>): TagRemoteKeyEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { tagEntity -> database.tagRemoteKeyDao().getRemoteKeys(tagEntity.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, TagEntity>): TagRemoteKeyEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { tagEntity -> database.tagRemoteKeyDao().getRemoteKeys(tagEntity.id) }
    }
}