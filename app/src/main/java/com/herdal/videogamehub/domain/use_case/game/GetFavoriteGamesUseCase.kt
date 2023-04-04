package com.herdal.videogamehub.domain.use_case.game

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.local.entity.toGameUiModel
import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val gameRepository: FavoriteGameRepository,
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val favoriteGames = gameRepository.getAll().map { entityList ->
                entityList.map { gameEntity -> gameEntity.toGameUiModel() }
            }
            Timber.d("$favoriteGames")
            emit(Resource.Success(data = favoriteGames))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}