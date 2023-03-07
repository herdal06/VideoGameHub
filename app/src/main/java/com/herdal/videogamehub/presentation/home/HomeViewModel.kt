package com.herdal.videogamehub.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.GetGamesUseCase
import com.herdal.videogamehub.domain.use_case.GetGenresUseCase
import com.herdal.videogamehub.domain.use_case.GetStoresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    getGenresUseCase: GetGenresUseCase,
    getStoresUseCase: GetStoresUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase
) : ViewModel() {

    private val _games =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val games = _games.asStateFlow()

    val genres = getGenresUseCase.invoke()

    val stores = getStoresUseCase.invoke()

    fun getGames() {
        getGamesUseCase().onEach {
            _games.emit(it)
        }.launchIn(viewModelScope)
    }

    fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch { // viewModelScope.launch runs on the main thread by default. no need to inject Dispatchers.Main
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }
}