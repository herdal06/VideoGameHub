package com.herdal.videogamehub.presentation.home.adapter.tag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BasePagingAdapter
import com.herdal.videogamehub.databinding.ItemTagBinding
import com.herdal.videogamehub.domain.ui_model.TagUiModel

class TagAdapter() : BasePagingAdapter<TagUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        TagViewHolder(
            ItemTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagViewHolder -> {
                getItem(position)?.let { tag -> holder.bind(tag) }
            }
        }
    }
}