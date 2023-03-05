package com.herdal.videogamehub.presentation.favorite.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.GetFavoriteGamesUseCase
import com.herdal.videogamehub.domain.use_case.SearchFavoriteGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteGamesViewModel @Inject constructor(
    getFavoriteGamesUseCase: GetFavoriteGamesUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val searchFavoriteGamesUseCase: SearchFavoriteGamesUseCase
) : ViewModel() {

    val favoriteGames = getFavoriteGamesUseCase.invoke()

    private val _searchedFavGames =
        MutableStateFlow<Resource<List<GameUiModel>>>(Resource.Loading())
    val searchedFavGames = _searchedFavGames.asStateFlow()

    fun searchFavGames(searchQuery: String) {
        searchFavoriteGamesUseCase(searchQuery = searchQuery)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _searchedFavGames.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        _searchedFavGames.value = resource.data?.let { Resource.Success(it) }!!
                    }
                    is Resource.Error -> {
                        _searchedFavGames.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun favoriteIconClicked(game: GameUiModel) {
        viewModelScope.launch {
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }
}