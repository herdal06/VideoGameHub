package com.herdal.videogamehub.presentation.home.adapter.tag

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemTagBinding
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class TagViewHolder(
    private val binding: ItemTagBinding,
    private val onClickTagClickHandler: OnTagClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tag: TagUiModel) = binding.apply {
        binding.executeWithAction {
            this.tag = tag
        }

        itemView.setOnClickListener {
            tag.id.let { tagId ->
                onClickTagClickHandler.onClickTag(tagId = tagId)
            }
        }
    }
}