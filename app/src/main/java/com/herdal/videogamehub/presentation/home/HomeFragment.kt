package com.herdal.videogamehub.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.herdal.videogamehub.databinding.FragmentHomeBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.favorite_games.adapter.OnFavoriteGameClickHandler
import com.herdal.videogamehub.presentation.home.adapter.game.GameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameListClickHandler
import com.herdal.videogamehub.presentation.home.adapter.genre.GenreAdapter
import com.herdal.videogamehub.presentation.home.adapter.genre.OnGenreListClickHandler
import com.herdal.videogamehub.presentation.home.adapter.store.StoreAdapter
import com.herdal.videogamehub.presentation.home.adapter.tag.OnTagClickHandler
import com.herdal.videogamehub.presentation.home.adapter.tag.TagAdapter
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var gameAdapter: GameAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var tagAdapter: TagAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        collectFlows()
        return view
    }

    private fun collectFlows() {
        // collect stores
        collectLatestLifecycleFlow(viewModel.stores) { stores ->
            storeAdapter.submitList(stores)
        }
        // collect genres
        collectLatestLifecycleFlow(viewModel.genres) { genres ->
            genreAdapter.submitList(genres)
        }
        // collect games
        viewModel.getGames()
        collectLatestLifecycleFlow(viewModel.games) { gamePagingData ->
            gameAdapter.submitData(gamePagingData)
        }
        // collect tags
        viewModel.getTags()
        collectLatestLifecycleFlow(viewModel.tags) { tagPagingData ->
            tagAdapter.submitData(tagPagingData)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        gameAdapter = GameAdapter(object : OnGameListClickHandler {
            override fun goToGameDetails(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }
        }, object : OnFavoriteGameClickHandler {
            override fun addGameToFavorite(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        genreAdapter = GenreAdapter(object : OnGenreListClickHandler {
            override fun goToGenreDetails(genreId: Int) {
                naviteToGenreDetails(genreId)
            }
        })
        storeAdapter = StoreAdapter()
        tagAdapter = TagAdapter(object : OnTagClickHandler {
            override fun onClickTag(tagId: Int) {
                navigateToGameByTagScreen(tagId)
            }
        })
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() = binding.apply {
        rvGames.adapter = gameAdapter
        rvGenres.adapter = genreAdapter
        rvStores.adapter = storeAdapter
        rvTags.adapter = tagAdapter
    }

    private fun navigateToGameByTagScreen(tagId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToGamesByTagFragment(tagId = tagId)
        findNavController().navigate(action)
    }

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    private fun naviteToGenreDetails(genreId: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToGenreDetailFragment(genreId = genreId)
        findNavController().navigate(action)
    }

    private fun onFavoriteGameIconClicked(game: GameUiModel) {
        lifecycleScope.launch {
            viewModel.favoriteGameIconClicked(game)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGames.adapter = null
        _binding = null
    }
}