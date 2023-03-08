package com.herdal.videogamehub.presentation.home.adapter.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herdal.videogamehub.common.base.BaseListAdapter
import com.herdal.videogamehub.databinding.ItemStoreBinding
import com.herdal.videogamehub.domain.ui_model.StoreUiModel

class StoreAdapter(
    private val onStoreListClickHandler: OnStoreListClickHandler
) : BaseListAdapter<StoreUiModel>(
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder =
        StoreViewHolder(
            ItemStoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onStoreListClickHandler
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StoreViewHolder -> {
                getItem(position)?.let { store -> holder.bind(store) }
            }
        }
    }
}