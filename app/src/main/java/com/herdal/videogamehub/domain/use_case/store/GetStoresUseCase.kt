package com.herdal.videogamehub.domain.use_case.store

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.data.local.entity.toStoreUiModel
import com.herdal.videogamehub.domain.repository.StoreRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val stores = storeRepository.getStores().map { res ->
                res.data?.map { storeEntity -> storeEntity.toStoreUiModel() }
            }
            Timber.d("$stores")
            emit(Resource.Success(data = stores))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}