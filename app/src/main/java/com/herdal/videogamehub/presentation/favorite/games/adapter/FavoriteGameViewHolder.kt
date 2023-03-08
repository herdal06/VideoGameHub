package com.herdal.videogamehub.presentation.favorite.games.adapter

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemFavoriteGameBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameListClickHandler
import com.herdal.videogamehub.utils.ext.executeWithAction

class FavoriteGameViewHolder(
    private val binding: ItemFavoriteGameBinding,
    private val onGameListClickHandler: OnGameListClickHandler,
    private val onFavoriteGameClickHandler: OnFavoriteGameClickHandler
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: GameUiModel) = binding.apply {
        binding.executeWithAction {
            this.game = game
        }

        itemView.setOnClickListener {
            game.id.let { it1 ->
                if (it1 != null) {
                    onGameListClickHandler.goToGameDetails(it1)
                }
            }
        }

        ibFavGame.setOnClickListener { onFavoriteGameClickHandler.addGameToFavorite(game) }
    }
}