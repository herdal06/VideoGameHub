package com.herdal.videogamehub.presentation.games_by_tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesByTagViewModel @Inject constructor(
    private val getGamesByTagUseCase: GetGamesByTagUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
) : ViewModel() {

    private val _gamesByTag =
        MutableStateFlow<PagingData<GameUiModel>>(PagingData.empty())
    val gamesByTag = _gamesByTag.asStateFlow()

    fun getGamesByTag(tagId: Int) {
        getGamesByTagUseCase(tagId = tagId)
            .onEach { pagingData ->
                _gamesByTag.value = pagingData
            }.launchIn(viewModelScope)
    }

    fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch { // viewModelScope.launch runs on the main thread by default. no need to inject Dispatchers.Main
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }
}