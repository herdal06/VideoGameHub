package com.herdal.videogamehub.presentation.search.adapter.genre_without_image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BaseListAdapter
import com.herdal.videogamehub.databinding.ItemGenreWithoutImageBinding
import com.herdal.videogamehub.domain.ui_model.GenreUiModel

class GenreWithoutImageAdapter(
    private val onGenreWithoutImageClickHandler: OnGenreWithoutImageClickHandler
) : BaseListAdapter<GenreUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        GenreWithoutImageViewHolder(
            ItemGenreWithoutImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onGenreWithoutImageClickHandler
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GenreWithoutImageViewHolder -> {
                getItem(position)?.let { genre -> holder.bind(genre) }
            }
        }
    }
}