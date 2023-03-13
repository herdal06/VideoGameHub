package com.herdal.videogamehub.domain.use_case.store

import com.herdal.videogamehub.data.local.entity.toStoreUiModel
import com.herdal.videogamehub.domain.repository.StoreRepository
import com.herdal.videogamehub.domain.ui_model.StoreUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    operator fun invoke(): Flow<List<StoreUiModel>?> {
        return storeRepository.getStores().map { resource ->
            resource.data?.map {
                it.toStoreUiModel()
            }
        }
    }
}