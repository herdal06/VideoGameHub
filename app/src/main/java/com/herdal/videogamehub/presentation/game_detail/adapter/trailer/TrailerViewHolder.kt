package com.herdal.videogamehub.presentation.game_detail.adapter.trailer

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemTrailerBinding
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class TrailerViewHolder(
    private val binding: ItemTrailerBinding,
    private val onTrailerClickListener: OnTrailerClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(trailer: TrailerUiModel) = binding.apply {
        binding.executeWithAction {
            this.trailer = trailer
        }

        itemView.setOnClickListener {
            trailer.data?.let {
                onTrailerClickListener.onClickVideo(it)
            }
        }
    }
}