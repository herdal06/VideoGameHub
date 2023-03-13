package com.herdal.videogamehub.presentation.search.adapter.genre_without_image

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemGenreWithoutImageBinding
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class GenreWithoutImageViewHolder(
    private val binding: ItemGenreWithoutImageBinding,
    private val onGenreWithoutImageClickHandler: OnGenreWithoutImageClickHandler
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: GenreUiModel) = binding.apply {
        binding.executeWithAction {
            this.genre = genre
        }

        itemView.setOnClickListener {
            genre.id.let { genreId ->
                onGenreWithoutImageClickHandler.getGamesByGenre(genreId)
            }
        }
    }
}