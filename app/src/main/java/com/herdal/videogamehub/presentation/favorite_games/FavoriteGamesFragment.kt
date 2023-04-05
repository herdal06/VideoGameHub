package com.herdal.videogamehub.presentation.favorite_games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.herdal.videogamehub.databinding.FragmentFavoriteGamesBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.favorite_games.adapter.FavoriteGameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameClickListener
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteGamesFragment : Fragment() {

    private var _binding: FragmentFavoriteGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteGamesViewModel by viewModels()

    private lateinit var favoriteGameAdapter: FavoriteGameAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteGamesBinding.inflate(inflater, container, false)
        val view = binding.root
        collectFavoriteGames()
        setupSearchView()
        return view
    }

    private fun setupRecyclerView() = binding.apply {
        favoriteGameAdapter = FavoriteGameAdapter(object : OnGameClickListener {
            override fun onGameClick(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }

            override fun onFavoriteGameClick(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        rvFavoriteGames.adapter = favoriteGameAdapter
    }

    private fun searchFavGames(searchQuery: String) = binding.apply {
        viewModel.handleEvent(FavoriteGamesUiEvent.SearchQueryChanged(searchQuery))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.searchedGames?.let {
                favoriteGameAdapter.submitList(it)
            }
        }
    }

    private fun collectFavoriteGames() {
        viewModel.handleEvent(FavoriteGamesUiEvent.GetFavoriteGames)
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.favoriteGames.let { flow ->
                flow?.collect {
                    favoriteGameAdapter.submitList(it)
                }
            }
        }
    }

    private fun setupSearchView() = binding.svFavoriteGames.setOnQueryTextListener(object :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                searchFavGames(query)
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText.isNullOrBlank()) {
                collectFavoriteGames()
            }
            return false
        }
    })

    private fun onFavoriteGameIconClicked(game: GameUiModel) {
        viewModel.handleEvent(FavoriteGamesUiEvent.FavoriteIconClicked(game))
    }

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            FavoriteGamesFragmentDirections.actionFavoriteGamesFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}