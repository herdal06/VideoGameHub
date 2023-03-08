package com.herdal.videogamehub.domain.use_case.game

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.repository.GameRepository
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetGameDetailsUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke(gameId: Int): Flow<Resource<GameUiModel>> = flow {
        try {
            emit(Resource.Loading())
            val meal = gameRepository.getGameDetails(gameId = gameId)
            Timber.d("$meal")
            emit(Resource.Success(data = meal))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}