package com.herdal.videogamehub.domain.use_case

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.repository.TagRepository
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    operator fun invoke(): Flow<PagingData<TagUiModel>> {
        return tagRepository.getTags()
    }
}