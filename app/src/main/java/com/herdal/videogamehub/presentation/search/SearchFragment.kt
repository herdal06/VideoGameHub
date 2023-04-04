package com.herdal.videogamehub.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.herdal.videogamehub.databinding.FragmentSearchBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.home.adapter.game.GameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameClickListener
import com.herdal.videogamehub.presentation.search.adapter.genre_without_image.GenreWithoutImageAdapter
import com.herdal.videogamehub.presentation.search.adapter.genre_without_image.OnGenreWithoutImageClickHandler
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import com.herdal.videogamehub.utils.ext.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchedGamesAdapter: GameAdapter
    private lateinit var genreAdapter: GenreWithoutImageAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        setupSearchView()
        collectGenres()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.apply {
        searchedGamesAdapter = GameAdapter(object : OnGameClickListener {
            override fun onGameClick(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }

            override fun onFavoriteGameClick(game: GameUiModel) {
                favoriteGameIconClicked(game)
            }
        })
        genreAdapter = GenreWithoutImageAdapter(object : OnGenreWithoutImageClickHandler {
            override fun getGamesByGenre(genreId: Int) {
                getGamesByGenreId(genreId = genreId)
            }
        })
        rvSearchedGames.adapter = searchedGamesAdapter
        rvGenreSearch.adapter = genreAdapter
    }

    private fun searchGames(searchText: String) {
        viewModel.handleEvent(SearchUiEvent.SearchGames(searchText))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.searchedGames?.let { flow ->
                binding.rvSearchedGames.show()
                flow.collect { searchedGames ->
                    searchedGamesAdapter.submitData(searchedGames)
                }
            }
        }
    }

    private fun collectGenres() = binding.apply {
        viewModel.handleEvent(SearchUiEvent.GetGenres)
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.genres.let { flow ->
                rvGenreSearch.show()
                flow?.collect { genres ->
                    genreAdapter.submitList(genres)
                }
            }
        }
    }

    private fun getGamesByGenreId(genreId: Int) {
        viewModel.handleEvent(SearchUiEvent.GetGamesByGenre(genreId))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.gamesByGenre.let { flow ->
                binding.rvGenreSearch.show()
                flow?.collect { games ->
                    searchedGamesAdapter.submitData(games)
                }
            }
        }
    }

    private fun setupSearchView() = binding.svGames.setOnQueryTextListener(object :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                searchGames(query)
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    private fun favoriteGameIconClicked(game: GameUiModel) {
        lifecycleScope.launch {
            viewModel.favoriteGameIconClicked(game)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}