package com.herdal.videogamehub.domain.use_case

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.local.entity.toGameUiModel
import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class SearchFavoriteGamesUseCase @Inject constructor(
    private val favoriteGameRepository: FavoriteGameRepository
) {
    operator fun invoke(searchQuery: String): Flow<Resource<List<GameUiModel>>> = flow {
        try {
            emit(Resource.Loading())
            val favoriteGames = favoriteGameRepository.findGamesWithName(search = searchQuery)
                .map { favoriteEntity -> favoriteEntity.toGameUiModel() }
            Timber.d("$favoriteGames")
            emit(Resource.Success(data = favoriteGames))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}