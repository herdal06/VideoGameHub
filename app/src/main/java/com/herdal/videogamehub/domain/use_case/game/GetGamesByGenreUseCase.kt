package com.herdal.videogamehub.domain.use_case.game

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.repository.GameRepository
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetGamesByGenreUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    operator fun invoke(genreId: Int) = flow {
        try {
            emit(Resource.Loading())
            val games = gameRepository.getGamesByGenre(genreId = genreId)
            Timber.d("$games")
            emit(Resource.Success(data = games))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}