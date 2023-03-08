package com.herdal.videogamehub.presentation.game_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BaseListAdapter
import com.herdal.videogamehub.databinding.ItemScreenshotBinding
import com.herdal.videogamehub.domain.ui_model.ScreenshotUiModel

class ScreenshotAdapter(
) : BaseListAdapter<ScreenshotUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        ScreenshotViewHolder(
            ItemScreenshotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScreenshotViewHolder -> {
                getItem(position)?.let { screenshot -> holder.bind(screenshot) }
            }
        }
    }
}