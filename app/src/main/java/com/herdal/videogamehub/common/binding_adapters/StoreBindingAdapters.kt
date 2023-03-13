package com.herdal.videogamehub.common.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.herdal.videogamehub.R

@BindingAdapter("android:setBackgroundResource")
fun setBackgroundResource(imageView: ImageView, name: String?) {
    when (name) {
        "Steam" -> {
            imageView.setImageResource(R.drawable.steam_logo)
        }
        "Xbox Store" -> {
            imageView.setImageResource(R.drawable.xbox_store)
        }
        "PlayStation Store" -> {
            imageView.setImageResource(R.drawable.playstation_store_logo)
        }
        "App Store" -> {
            imageView.setImageResource(R.drawable.appstore_logo)
        }
        "GOG" -> {
            imageView.setImageResource(R.drawable.gog_logo)
        }
        "Nintendo Store"-> {
            imageView.setImageResource(R.drawable.nintendo_logo)
        }
        "Xbox 360 Store"-> {
            imageView.setImageResource(R.drawable.xbox360_logo)
        }
        "Google Play" -> {
            imageView.setImageResource(R.drawable.playstore_logo)
        }
        "itch.io" -> {
            imageView.setImageResource(R.drawable.itch_logo)
        }
        "Epic Games" -> {
            imageView.setImageResource(R.drawable.epicgames_logo)
        }
    }
}