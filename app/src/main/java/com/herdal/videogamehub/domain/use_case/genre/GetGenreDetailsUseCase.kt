package com.herdal.videogamehub.domain.use_case.genre

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.remote.dto.genre.genre_detail.toGenreUiModel
import com.herdal.videogamehub.domain.repository.GenreRepository
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetGenreDetailsUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {
    operator fun invoke(genreId: Int): Flow<Resource<GenreUiModel>> = flow {
        try {
            emit(Resource.Loading())
            val genreDetails = genreRepository.getGenreDetails(id = genreId).toGenreUiModel()
            emit(Resource.Success(data = genreDetails))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}