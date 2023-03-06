package com.herdal.videogamehub.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.herdal.videogamehub.data.remote.dto.game.GameDto
import com.herdal.videogamehub.data.remote.service.GameService
import okio.IOException
import retrofit2.HttpException

class GamesByGenrePagingSource(
    private val gameService: GameService,
    private val genreId: Int
) : PagingSource<Int, GameDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameDto> {
        val pageIndex = params.key ?: 1
        return try {
            val response = gameService.getGamesByGenre(page = pageIndex, genreId = genreId)
            val games = response.results
            val nextKey =
                if (games.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / 20)
                }
            LoadResult.Page(
                data = games,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, GameDto>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}