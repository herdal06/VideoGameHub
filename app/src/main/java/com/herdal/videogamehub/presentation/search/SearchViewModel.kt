package com.herdal.videogamehub.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.SearchGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGamesUseCase: SearchGamesUseCase
) : ViewModel() {

    private val _searchedGames =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val searchedGames = _searchedGames.asStateFlow()

    fun searchGames(searchQuery: String) {
        if(searchQuery.isNotEmpty()) {
            searchGamesUseCase(searchQuery = searchQuery)
                .onEach { pagingData ->
                    _searchedGames.value = pagingData
                }.launchIn(viewModelScope)
        }
    }
}