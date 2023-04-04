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
import com.herdal.videogamehub.databinding.FragmentGenreDetailBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.presentation.home.adapter.game.GameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameClickListener
import com.herdal.videogamehub.utils.ext.*
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
        collectGenre(getGenreIdArgs())
        getGamesByGenre(getGenreIdArgs())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        addTransitionToDescriptionText()
    }

    private fun collectGenre(genreId: Int) = binding.apply {
        viewModel.handleEvent(GenreDetailUiEvent.GetGenreDetails(genreId))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.genre?.let { genre ->
                pbGenreDetails.hide()
                tvGenreDetailError.hide()
                setupUI(genre)
            }
        }
    }

    private fun setupRecyclerView() = binding.apply {
        gamesByGenreAdapter = GameAdapter(object : OnGameClickListener {
            override fun onGameClick(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }

            override fun onFavoriteGameClick(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        rvGamesByGenre.adapter = gamesByGenreAdapter
    }

    private fun getGamesByGenre(genreId: Int) = binding.apply {
        viewModel.handleEvent(GenreDetailUiEvent.GetGamesByGenre(genreId))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.games?.let { flow ->
                flow.collect {
                    gamesByGenreAdapter.submitData(it)
                }
            }
        }
    }

    private fun onFavoriteGameIconClicked(game: GameUiModel) {
        lifecycleScope.launch {
            viewModel.handleEvent(GenreDetailUiEvent.FavoriteGameIconClicked(game))
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

    private fun addTransitionToDescriptionText() = binding.apply {
        tvDescriptionText.setOnClickListener {
            if (tvGenreDescription.isGone()) {
                tvGenreDescription.show()
                tvGenreDescription.expand(500)
            } else {
                tvGenreDescription.gone()
                tvGenreDescription.collapse(500)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}