package com.herdal.videogamehub.data.repository

import androidx.room.withTransaction
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.local.entity.ScreenshotEntity
import com.herdal.videogamehub.data.remote.dto.game_screenshots.toScreenshotEntity
import com.herdal.videogamehub.data.remote.service.ScreenshotService
import com.herdal.videogamehub.domain.repository.ScreenshotRepository
import com.herdal.videogamehub.utils.Resource
import com.herdal.videogamehub.utils.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScreenshotRepositoryImpl @Inject constructor(
    private val screenshotService: ScreenshotService,
    private val database: AppDatabase
) : ScreenshotRepository {

    private val screenShotDao = database.screenShotDao()

    override fun getGameScreenshots(gameId: Int): Flow<Resource<List<ScreenshotEntity>>> =
        networkBoundResource(
            query = {
                screenShotDao.getAll()
            },
            // Just for testing purpose,
            // a delay of 2 second is set.
            fetch = {
                //delay(2000)
                screenshotService.getGameScreenshots(gameId = gameId)
            },
            // Save the results in the table.
            // If data exists, then delete it
            // and then store.
            saveFetchResult = { gameResponse ->
                database.withTransaction {
                    screenShotDao.deleteAll()
                    screenShotDao.insertAll(gameResponse.results.map { screenShotDto -> screenShotDto.toScreenshotEntity() })
                }
            }
        )
}