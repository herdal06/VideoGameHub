package com.herdal.videogamehub.presentation.game_detail.adapter.screenshot

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemScreenshotBinding
import com.herdal.videogamehub.domain.ui_model.ScreenshotUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class ScreenshotViewHolder(
    private val binding: ItemScreenshotBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(screenshot: ScreenshotUiModel) = binding.apply {
        binding.executeWithAction {
            this.screenshot = screenshot
        }
    }
}