package com.herdal.videogamehub.domain.use_case.tag

import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.repository.TagRepository
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val tags = tagRepository.getTags()
            Timber.d("$tags")
            emit(Resource.Success(data = tags))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        }
    }
}