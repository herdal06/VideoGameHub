package com.herdal.videogamehub.domain.use_case

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.remote.dto.game_trailers.toTrailerUiModel
import com.herdal.videogamehub.domain.repository.TrailerRepository
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetGameTrailersUseCase @Inject constructor(
    private val trailerRepository: TrailerRepository
) {
    suspend operator fun invoke(gameId: Int): Flow<Resource<List<TrailerUiModel>?>> = flow {
        try {
            emit(Resource.Loading())
            val trailers =
                trailerRepository.getTrailers(gameId = gameId).results.map { trailerDto ->
                    trailerDto.toTrailerUiModel()
                }
            emit(Resource.Success(data = trailers))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}