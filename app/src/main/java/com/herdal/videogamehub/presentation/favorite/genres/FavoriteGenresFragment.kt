package com.herdal.videogamehub.presentation.favorite.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.herdal.videogamehub.databinding.FragmentFavoriteGenresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteGenresFragment : Fragment() {

    private var _binding: FragmentFavoriteGenresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteGenresBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}