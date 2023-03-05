package com.herdal.videogamehub.presentation.favorite.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.databinding.FragmentFavoriteGamesBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.favorite.FavoriteFragmentDirections
import com.herdal.videogamehub.presentation.favorite.games.adapter.FavoriteGameAdapter
import com.herdal.videogamehub.presentation.home.OnFavoriteGameClickHandler
import com.herdal.videogamehub.presentation.home.OnGameListClickHandler
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
        favoriteGameAdapter = FavoriteGameAdapter(object : OnGameListClickHandler {
            override fun goToGameDetails(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }
        }, object : OnFavoriteGameClickHandler {
            override fun addGameToFavorite(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        rvFavoriteGames.adapter = favoriteGameAdapter
    }

    private fun searchFavGames(searchQuery: String) = binding.apply {
        viewModel.searchFavGames(searchQuery)
        collectLatestLifecycleFlow(viewModel.searchedFavGames) { resource ->
            when (resource) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                    favoriteGameAdapter.submitList(resource.data)
                }
                is Resource.Error -> {

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
            return false
        }
    })

    private fun onFavoriteGameIconClicked(game: GameUiModel) {
        viewModel.favoriteIconClicked(game)
    }

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            FavoriteFragmentDirections.actionFavoriteFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    private fun collectFavoriteGames() {
        collectLatestLifecycleFlow(viewModel.favoriteGames) { result ->
            favoriteGameAdapter.submitList(result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding.rvFavoriteGames.adapter = null
    }
}