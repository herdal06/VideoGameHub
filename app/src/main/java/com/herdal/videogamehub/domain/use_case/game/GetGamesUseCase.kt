package com.herdal.videogamehub.domain.use_case.game

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.repository.GameRepository
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val games = gameRepository.getGames()
            Timber.d("$games")
            emit(Resource.Success(data = games))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}