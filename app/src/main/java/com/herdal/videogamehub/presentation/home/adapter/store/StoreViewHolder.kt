package com.herdal.videogamehub.presentation.home.adapter.store

import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.databinding.ItemStoreBinding
import com.herdal.videogamehub.domain.ui_model.StoreUiModel
import com.herdal.videogamehub.utils.ext.executeWithAction

class StoreViewHolder(
    private val binding: ItemStoreBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(store: StoreUiModel) = binding.apply {
        binding.executeWithAction {
            this.store = store
        }
    }
}