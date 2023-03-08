package com.herdal.videogamehub.domain.use_case.genre

import com.herdal.videogamehub.data.local.entity.toGenreUiModel
import com.herdal.videogamehub.domain.repository.GenreRepository
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) {
    operator fun invoke(): Flow<List<GenreUiModel>?> {
        return genreRepository.getGenres().map { res ->
            res.data?.map { genreEntity -> genreEntity.toGenreUiModel() }
        }
    }
}