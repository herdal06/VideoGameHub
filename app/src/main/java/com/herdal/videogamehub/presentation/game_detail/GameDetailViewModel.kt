package com.herdal.videogamehub.presentation.game_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase
) : ViewModel() {
    private val _gameDetail =
        MutableStateFlow<Resource<GameUiModel>>(Resource.Loading())
    val gameDetail = _gameDetail.asStateFlow()

    fun getGameById(id: Int) {
        getGameDetailsUseCase.invoke(id)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _gameDetail.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            _gameDetail.value = Resource.Success(resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _gameDetail.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}
