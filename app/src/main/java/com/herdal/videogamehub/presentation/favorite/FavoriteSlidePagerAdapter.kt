package com.herdal.videogamehub.presentation.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.herdal.videogamehub.presentation.favorite.games.FavoriteGamesFragment
import com.herdal.videogamehub.presentation.favorite.genres.FavoriteGenresFragment

class FavoriteSlidePagerAdapter(
    fa: FragmentActivity,
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return FavoriteGamesFragment()
            }
            1 -> {
                return FavoriteGenresFragment()
            }
        }
        return Fragment()
    }
}