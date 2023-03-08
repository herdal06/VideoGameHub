package com.herdal.videogamehub.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenresUseCase
import com.herdal.videogamehub.domain.use_case.store.GetStoresUseCase
import com.herdal.videogamehub.domain.use_case.tag.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    getGenresUseCase: GetGenresUseCase,
    getStoresUseCase: GetStoresUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _games =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val games = _games.asStateFlow()

    val genres = getGenresUseCase.invoke()

    val stores = getStoresUseCase.invoke()

    private val _tags =
        MutableStateFlow<PagingData<TagUiModel>>(PagingData.empty())
    val tags = _tags.asStateFlow()

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

    fun getTags() {
        getTagsUseCase().onEach {
            _tags.emit(it)
        }.launchIn(viewModelScope)
    }
}