package com.herdal.videogamehub.presentation.home.adapter.genre

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemGenreBinding
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class GenreViewHolder(
    private val binding: ItemGenreBinding,
    private val onGenreClickListener: OnGenreClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: GenreUiModel) = binding.apply {
        binding.executeWithAction {
            this.genre = genre
        }

        itemView.setOnClickListener {
            genre.id.let { genreId ->
                onGenreClickListener.goToGenreDetails(genreId)
            }
        }
    }
}