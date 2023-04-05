package com.herdal.videogamehub.domain.use_case.game_screenshots

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.local.entity.toScreenshotUiModel
import com.herdal.videogamehub.domain.repository.ScreenshotRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetScreenshotsUseCase @Inject constructor(
    private val screenshotRepository: ScreenshotRepository
) {
    operator fun invoke(gameId: Int) = flow {
        try {
            emit(Resource.Loading())
            val screenshots =
                screenshotRepository.getGameScreenshots(gameId = gameId).map { resource ->
                    resource.data?.map {
                        it.toScreenshotUiModel()
                    }
                }
            Timber.d("$screenshots")
            emit(Resource.Success(data = screenshots))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message))
        }
    }
}