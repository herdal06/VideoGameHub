package com.herdal.videogamehub.common.binding_adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.herdal.videogamehub.R

@BindingAdapter("android:isFavorite")
fun isFavorite(view: View, isFavorite: Boolean) {
    if (isFavorite) {
        view.setBackgroundResource(R.drawable.ic_favorite_essential)
    } else {
        view.setBackgroundResource(R.drawable.ic_favorite_border_essential)
    }
}