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
import com.herdal.videogamehub.presentation.home.adapter.game.GameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameClickListener
import com.herdal.videogamehub.presentation.home.adapter.genre.GenreAdapter
import com.herdal.videogamehub.presentation.home.adapter.genre.OnGenreClickListener
import com.herdal.videogamehub.presentation.home.adapter.store.StoreAdapter
import com.herdal.videogamehub.presentation.home.adapter.tag.OnTagClickListener
import com.herdal.videogamehub.presentation.home.adapter.tag.TagAdapter
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import com.herdal.videogamehub.utils.ext.show
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        collectData()
    }

    private fun collectGames() = binding.apply {
        viewModel.handleEvent(HomeUiEvent.GetGames)
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.games.let { flow ->
                rvGames.show()
                flow?.collect { games ->
                    gameAdapter.submitData(games)
                }
            }
        }
    }

    private fun collectTags() = binding.apply {
        viewModel.handleEvent(HomeUiEvent.GetTags)
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.tags.let { flow ->
                rvTags.show()
                flow?.collect { tags ->
                    tagAdapter.submitData(tags)
                }
            }
        }
    }

    private fun collectStores() = binding.apply {
        viewModel.handleEvent(HomeUiEvent.GetStores)
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.stores.let { flow ->
                rvStores.show()
                flow?.collect { stores ->
                    storeAdapter.submitList(stores)
                }
            }
        }
    }

    private fun collectGenres() = binding.apply {
        viewModel.handleEvent(HomeUiEvent.GetGenres)
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.genres.let { flow ->
                rvGenres.show()
                flow?.collect { genres ->
                    genreAdapter.submitList(genres)
                }
            }
        }
    }

    private fun collectData() {
        collectGames()
        collectTags()
        collectStores()
        collectGenres()
    }

    private fun initRecyclerViews() {
        gameAdapter = GameAdapter(object : OnGameClickListener {
            override fun onGameClick(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }

            override fun onFavoriteGameClick(game: GameUiModel) {
                onFavoriteGameIconClicked(game)
            }
        })
        genreAdapter = GenreAdapter(object : OnGenreClickListener {
            override fun goToGenreDetails(genreId: Int) {
                naviteToGenreDetails(genreId)
            }
        })
        storeAdapter = StoreAdapter()
        tagAdapter = TagAdapter(object : OnTagClickListener {
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
            viewModel.handleEvent(HomeUiEvent.FavoriteGameIconClicked(game))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGames.adapter = null
        _binding = null
    }
}