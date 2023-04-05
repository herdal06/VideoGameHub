package com.herdal.videogamehub.domain.use_case.genre

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.local.entity.toGenreUiModel
import com.herdal.videogamehub.domain.repository.GenreRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val genres = genreRepository.getGenres().map { res ->
                res.data?.map { genreEntity -> genreEntity.toGenreUiModel() }
            }
            Timber.d("$genres")
            emit(Resource.Success(data = genres))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}