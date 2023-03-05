package com.herdal.videogamehub.data.repository

import androidx.room.withTransaction
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.remote.dto.genre.toGenreEntity
import com.herdal.videogamehub.data.remote.service.GenreService
import com.herdal.videogamehub.domain.repository.GenreRepository
import com.herdal.videogamehub.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreService: GenreService,
    private val db: AppDatabase,
) : GenreRepository {
    private val genreDao = db.genreDao()
    override fun getGenres() = networkBoundResource(
        query = {
            genreDao.getAll()
        },
        // Just for testing purpose,
        // a delay of 2 second is set.
        fetch = {
            delay(2000)
            genreService.getGenres()
        },
        // Save the results in the table.
        // If data exists, then delete it
        // and then store.
        saveFetchResult = { gameResponse ->
            db.withTransaction {
                genreDao.deleteAll()
                genreDao.insertAll(gameResponse.results.map { genreDto -> genreDto.toGenreEntity() })
            }
        }
    )
}