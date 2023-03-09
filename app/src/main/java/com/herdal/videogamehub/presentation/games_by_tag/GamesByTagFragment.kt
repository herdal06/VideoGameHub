package com.herdal.videogamehub.presentation.games_by_tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.herdal.videogamehub.databinding.FragmentGamesByTagBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.favorite.games.adapter.OnFavoriteGameClickHandler
import com.herdal.videogamehub.presentation.home.adapter.game.GameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameListClickHandler
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesByTagFragment : Fragment() {

    private var _binding: FragmentGamesByTagBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesByTagViewModel by viewModels()

    private val navigationArgs: GamesByTagFragmentArgs by navArgs()

    private fun getTagIdArgs() = navigationArgs.tagId

    private lateinit var gamesByTagAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesByTagBinding.inflate(inflater, container, false)
        val view = binding.root
        getGamesByTag(getTagIdArgs())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.apply {
        gamesByTagAdapter = GameAdapter(object : OnGameListClickHandler {
            override fun goToGameDetails(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }
        }, object : OnFavoriteGameClickHandler {
            override fun addGameToFavorite(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        rvGamesByTag.adapter = gamesByTagAdapter
    }

    private fun getGamesByTag(tagId: Int) {
        viewModel.getGamesByTag(tagId)
        collectLatestLifecycleFlow(viewModel.gamesByTag) { pagingData ->
            gamesByTagAdapter.submitData(pagingData)
        }
    }

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            GamesByTagFragmentDirections.actionGamesByTagFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    private fun onFavoriteGameIconClicked(game: GameUiModel) {
        lifecycleScope.launch {
            viewModel.favoriteGameIconClicked(game)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}