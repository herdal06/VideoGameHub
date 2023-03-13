package com.herdal.videogamehub.domain.repository

import com.herdal.videogamehub.data.local.entity.StoreEntity
import com.herdal.videogamehub.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getStores(): Flow<Resource<List<StoreEntity>>>
}