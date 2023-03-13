package com.herdal.videogamehub.data.repository

import androidx.room.withTransaction
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.data.local.entity.StoreEntity
import com.herdal.videogamehub.data.remote.dto.store.toStoreEntity
import com.herdal.videogamehub.data.remote.service.StoreService
import com.herdal.videogamehub.domain.repository.StoreRepository
import com.herdal.videogamehub.utils.Resource
import com.herdal.videogamehub.utils.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeService: StoreService,
    private val db: AppDatabase,
) : StoreRepository {

    private val storeDao = db.storeDao()

    override fun getStores(): Flow<Resource<List<StoreEntity>>> = networkBoundResource(
        query = {
            storeDao.getAll()
        },
        // Just for testing purpose,
        // a delay of 2 second is set.
        fetch = {
            delay(2000)
            storeService.getStores()
        },
        // Save the results in the table.
        // If data exists, then delete it
        // and then store.
        saveFetchResult = { gameResponse ->
            db.withTransaction {
                storeDao.deleteAll()
                storeDao.insertAll(gameResponse.results.map { storeDto -> storeDto.toStoreEntity() })
            }
        }
    )
}