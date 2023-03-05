package com.herdal.videogamehub.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.GetGamesUseCase
import com.herdal.videogamehub.domain.use_case.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _games =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val games = _games.asStateFlow()

    val genres = getGenresUseCase.invoke()

    fun getGames() {
        getGamesUseCase().onEach {
            _games.emit(it)
        }.launchIn(viewModelScope)
    }
}