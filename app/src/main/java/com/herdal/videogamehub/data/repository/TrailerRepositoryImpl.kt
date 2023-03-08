package com.herdal.videogamehub.data.repository

import com.herdal.videogamehub.data.remote.dto.game_trailers.TrailerResponse
import com.herdal.videogamehub.data.remote.service.TrailerService
import com.herdal.videogamehub.domain.repository.TrailerRepository
import javax.inject.Inject

class TrailerRepositoryImpl @Inject constructor(
    private val trailerService: TrailerService,
) : TrailerRepository {

    override suspend fun getTrailers(gameId: Int): TrailerResponse {
        return trailerService.getGameTrailers(gameId)
    }
}