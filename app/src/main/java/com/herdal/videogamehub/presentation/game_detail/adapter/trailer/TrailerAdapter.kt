package com.herdal.videogamehub.presentation.game_detail.adapter.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BaseListAdapter
import com.herdal.videogamehub.databinding.ItemTrailerBinding
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel

class TrailerAdapter(
    private val onTrailerClickHandler: OnTrailerClickHandler
) : BaseListAdapter<TrailerUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        TrailerViewHolder(
            ItemTrailerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onTrailerClickHandler
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrailerViewHolder -> {
                getItem(position)?.let { trailer -> holder.bind(trailer) }
            }
        }
    }
}