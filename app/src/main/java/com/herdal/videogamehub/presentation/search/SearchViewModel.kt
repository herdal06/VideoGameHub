package com.herdal.videogamehub.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByGenreUseCase
import com.herdal.videogamehub.domain.use_case.game.SearchGamesUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGamesUseCase: SearchGamesUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase,
) : ViewModel() {

    private val _searchedGames =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val searchedGames = _searchedGames.asStateFlow()

    private val _genres =
        MutableStateFlow<List<GenreUiModel>>(emptyList())
    val genres = _genres.asStateFlow()

    private val _gamesByGenre =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val gamesByGenre = _gamesByGenre.asStateFlow()

    fun searchGames(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            searchGamesUseCase(searchQuery = searchQuery)
                .onEach { pagingData ->
                    _searchedGames.value = pagingData
                }.launchIn(viewModelScope)
        }
    }

    fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch { // viewModelScope.launch runs on the main thread by default. no need to inject Dispatchers.Main
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }

    fun getGenres() {
        getGenresUseCase().onEach {
            it?.let { it1 -> _genres.emit(it1) }
        }.launchIn(viewModelScope)
    }

    fun getGamesByGenre(genreId: Int) {
        getGamesByGenreUseCase(genreId = genreId)
            .onEach { pagingData ->
                _gamesByGenre.value = pagingData
            }.launchIn(viewModelScope)
    }
}