package com.herdal.videogamehub.presentation.favorite_games.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BaseListAdapter
import com.herdal.videogamehub.databinding.ItemFavoriteGameBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameClickListener

class FavoriteGameAdapter(
    private val onGameClickListener: OnGameClickListener,
) : BaseListAdapter<GameUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        FavoriteGameViewHolder(
            ItemFavoriteGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onGameClickListener
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteGameViewHolder -> {
                getItem(position)?.let { favoriteGame -> holder.bind(favoriteGame) }
            }
        }
    }
}