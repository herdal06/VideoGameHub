package com.herdal.videogamehub.presentation.game_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.herdal.videogamehub.R
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.databinding.FragmentGameDetailBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding

    private val viewModel: GameDetailViewModel by viewModels()

    private val navigationArgs: GameDetailFragmentArgs by navArgs()

    private fun getGameId() = navigationArgs.gameId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_detail, container, false)
        val view = binding.root
        collectGameDetails(getGameId())
        return view
    }

    private fun collectGameDetails(gameId: Int) {
        viewModel.getGameById(gameId)
        collectLatestLifecycleFlow(viewModel.gameDetail) { resource ->
            when (resource) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                    resource.data?.let { setupUI(it) }
                }
                is Resource.Error -> {

                }
            }
        }
    }

    private fun setupUI(game: GameUiModel) = binding.apply {
        binding.game = game
    }
}