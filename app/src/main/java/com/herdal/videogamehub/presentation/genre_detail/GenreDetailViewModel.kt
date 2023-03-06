package com.herdal.videogamehub.presentation.genre_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.domain.use_case.GetGenreDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenreDetailViewModel @Inject constructor(
    private val getGenreDetailsUseCase: GetGenreDetailsUseCase
) : ViewModel() {

    private val _genreDetails =
        MutableStateFlow<Resource<GenreUiModel>>(Resource.Loading())
    val genre = _genreDetails.asStateFlow()

    fun getGenreDetails(genreId: Int) {
        getGenreDetailsUseCase.invoke(genreId = genreId)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _genreDetails.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            _genreDetails.value = Resource.Success(resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _genreDetails.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}