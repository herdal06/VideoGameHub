package com.herdal.videogamehub.presentation.home.adapter.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BaseListAdapter
import com.herdal.videogamehub.databinding.ItemGenreBinding
import com.herdal.videogamehub.domain.ui_model.GenreUiModel

class GenreAdapter(
    private val onGenreListClickHandler: OnGenreListClickHandler
) : BaseListAdapter<GenreUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        GenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onGenreListClickHandler
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GenreViewHolder -> {
                getItem(position)?.let { genre -> holder.bind(genre) }
            }
        }
    }
}