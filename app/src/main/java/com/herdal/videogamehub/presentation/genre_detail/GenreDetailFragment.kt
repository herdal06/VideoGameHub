package com.herdal.videogamehub.presentation.genre_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.databinding.FragmentGenreDetailBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.presentation.home.OnFavoriteGameClickHandler
import com.herdal.videogamehub.presentation.home.OnGameListClickHandler
import com.herdal.videogamehub.presentation.home.adapter.GameAdapter
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import com.herdal.videogamehub.utils.ext.hide
import com.herdal.videogamehub.utils.ext.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GenreDetailFragment : Fragment() {

    private var _binding: FragmentGenreDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GenreDetailViewModel by viewModels()

    private val navigationArgs: GenreDetailFragmentArgs by navArgs()

    private fun getGenreIdArgs() = navigationArgs.genreId

    private lateinit var gamesByGenreAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        collectGenreDetails(getGenreIdArgs())
        getGamesByGenre(getGenreIdArgs())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun collectGenreDetails(genreId: Int) = binding.apply {
        viewModel.getGenreDetails(genreId)
        collectLatestLifecycleFlow(viewModel.genre) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    pbGenreDetails.show()
                    tvGenreDetailError.hide()
                }
                is Resource.Success -> {
                    pbGenreDetails.hide()
                    tvGenreDetailError.hide()
                    setupUI(resource.data)
                }
                is Resource.Error -> {
                    pbGenreDetails.hide()
                    tvGenreDetailError.show()
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.apply {
        gamesByGenreAdapter = GameAdapter(object : OnGameListClickHandler {
            override fun goToGameDetails(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }
        }, object : OnFavoriteGameClickHandler {
            override fun addGameToFavorite(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        rvGamesByGenre.adapter = gamesByGenreAdapter
    }

    private fun getGamesByGenre(genreId: Int) = binding.apply {
        viewModel.getGamesByGenre(genreId)
        collectLatestLifecycleFlow(viewModel.gamesByGenre) { pagingData ->
            gamesByGenreAdapter.submitData(pagingData)
        }
    }

    private fun onFavoriteGameIconClicked(game: GameUiModel) {
        lifecycleScope.launch {
            viewModel.favoriteGameIconClicked(game)
        }
    }

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            GenreDetailFragmentDirections.actionGenreDetailFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    private fun setupUI(genre: GenreUiModel?) = binding.apply {
        binding.genre = genre
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}