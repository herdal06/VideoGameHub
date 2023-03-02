package com.herdal.videogamehub.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemGameBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.home.OnGameListClickHandler
import com.herdal.videogamehub.utils.ext.executeWithAction

class GameViewHolder(
    private val binding: ItemGameBinding,
    private val onGameListClickHandler: OnGameListClickHandler
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: GameUiModel) = binding.apply {
        binding.executeWithAction {
            this.game = game
        }

        itemView.setOnClickListener {
            game.id.let { it1 -> onGameListClickHandler.goToGameDetails(it1) }
        }
    }
}