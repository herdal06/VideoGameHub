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
import com.google.android.material.snackbar.Snackbar
import com.herdal.videogamehub.databinding.FragmentGamesByTagBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.home.adapter.game.GameAdapter
import com.herdal.videogamehub.presentation.home.adapter.game.OnGameClickListener
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

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
        setupUiEvents()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.handleEvent(GamesByTagUiEvent.GetGamesByTag(getTagIdArgs()))
    }

    private fun setupRecyclerView() = binding.apply {
        gamesByTagAdapter = GameAdapter(object : OnGameClickListener {
            override fun onGameClick(gameId: Int) {
                goToGameDetailsScreen(gameId)
            }

            override fun onFavoriteGameClick(game: GameUiModel) {
                viewModel.handleEvent(GamesByTagUiEvent.FavoriteGameIconClicked(game))
            }
        })
        rvGamesByTag.adapter = gamesByTagAdapter
    }

    private fun setupUiEvents() {
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            handleUiState(state)
        }
    }

    private fun handleUiState(uiState: GamesByTagUiState) {
        binding.apply {
            uiState.error?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }

            //progressBar.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            uiState.games?.let { flow ->
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    flow.collect { games ->
                        gamesByTagAdapter.submitData(games)
                    }
                }
            }
        }
    }

    private fun goToGameDetailsScreen(gameId: Int) {
        val action =
            GamesByTagFragmentDirections.actionGamesByTagFragmentToGameDetailFragment(gameId = gameId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}