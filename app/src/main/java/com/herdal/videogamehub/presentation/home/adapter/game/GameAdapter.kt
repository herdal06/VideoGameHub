package com.herdal.videogamehub.presentation.home.adapter.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BasePagingAdapter
import com.herdal.videogamehub.databinding.ItemGameBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel

class GameAdapter(
    private val onGameClickListener: OnGameClickListener,
) : BasePagingAdapter<GameUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        GameViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onGameClickListener
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameViewHolder -> {
                getItem(position)?.let { game -> holder.bind(game) }
            }
        }
    }
}