package com.herdal.videogamehub.presentation.home.adapter.game

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemGameBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class GameViewHolder(
    private val binding: ItemGameBinding,
    private val onGameClickListener: OnGameClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: GameUiModel) = binding.apply {
        binding.executeWithAction {
            this.game = game
        }

        itemView.setOnClickListener {
            game.id.let { it1 ->
                if (it1 != null) {
                    onGameClickListener.onGameClick(it1)
                }
            }
        }
        ibFavGame.setOnClickListener { onGameClickListener.onFavoriteGameClick(game) }
    }
}