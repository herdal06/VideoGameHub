package com.herdal.videogamehub.domain.repository

import com.herdal.videogamehub.data.remote.dto.game_trailers.TrailerResponse

interface TrailerRepository {

    suspend fun getTrailers(gameId: Int): TrailerResponse
}