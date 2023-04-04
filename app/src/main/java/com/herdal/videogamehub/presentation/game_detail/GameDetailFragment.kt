package com.herdal.videogamehub.presentation.game_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.herdal.videogamehub.R
import com.herdal.videogamehub.databinding.FragmentGameDetailBinding
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.presentation.game_detail.adapter.screenshot.ScreenshotAdapter
import com.herdal.videogamehub.presentation.game_detail.adapter.trailer.OnTrailerClickListener
import com.herdal.videogamehub.presentation.game_detail.adapter.trailer.TrailerAdapter
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailBinding

    private val viewModel: GameDetailViewModel by viewModels()

    private val navigationArgs: GameDetailFragmentArgs by navArgs()

    private fun getGameId() = navigationArgs.gameId

    private val screenshotAdapter: ScreenshotAdapter by lazy { ScreenshotAdapter() }
    private lateinit var trailerAdapter: TrailerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_detail, container, false)
        val view = binding.root
        collectData(getGameId())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun collectGameDetails(gameId: Int) = binding.apply {
        viewModel.handleEvent(GameDetailUiEvent.GetGameById(gameId))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.gameDetail?.let {
                setupUI(it)
            }
        }
    }

    private fun setupUI(game: GameUiModel) = binding.apply {
        binding.game = game
    }

    private fun setupRecyclerView() = binding.apply {
        trailerAdapter = TrailerAdapter(object : OnTrailerClickListener {
            override fun onClickVideo(url: String?) {
                Toast.makeText(requireContext(), "Test", Toast.LENGTH_LONG).show()
            }
        })
        rvScreenshots.adapter = screenshotAdapter
        rvTrailers.adapter = trailerAdapter
    }

    private fun collectData(gameId:Int) {
        collectGameDetails(gameId)
        collectScreenshots(gameId)
        collectTrailers(gameId)
    }

    private fun collectScreenshots(gameId: Int) {
        viewModel.handleEvent(GameDetailUiEvent.GetScreenshots(gameId))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.screenshots?.let { flow ->
                flow.collect {
                    screenshotAdapter.submitList(it)
                }
            }
        }
    }

    private fun collectTrailers(gameId: Int) {
        viewModel.handleEvent(GameDetailUiEvent.GetTrailers(gameId))
        collectLatestLifecycleFlow(viewModel.uiState) { state ->
            state.trailers?.let {
                trailerAdapter.submitList(it)
            }
        }
    }
}