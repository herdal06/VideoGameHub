package com.herdal.videogamehub.domain.use_case.game_screenshots

import com.herdal.videogamehub.data.local.entity.toScreenshotUiModel
import com.herdal.videogamehub.domain.repository.ScreenshotRepository
import com.herdal.videogamehub.domain.ui_model.ScreenshotUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetScreenshotsUseCase @Inject constructor(
    private val screenshotRepository: ScreenshotRepository
) {
    operator fun invoke(gameId: Int): Flow<List<ScreenshotUiModel>?> {
        return screenshotRepository.getGameScreenshots(gameId = gameId).map { resource ->
            resource.data?.map {
                it.toScreenshotUiModel()
            }
        }
    }
}