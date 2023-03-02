package com.herdal.videogamehub.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.herdal.videogamehub.databinding.FragmentHomeBinding
import com.herdal.videogamehub.presentation.home.adapter.GameAdapter
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val gameAdapter: GameAdapter by lazy {
        GameAdapter(::onGameClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        collectGames()
        return view
    }

    private fun collectGames() {
        viewModel.getGames()
        collectLatestLifecycleFlow(viewModel.games) {
            gameAdapter.submitData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() = binding.apply {
        rvGames.adapter = gameAdapter
    }

    private fun onGameClick(gameId: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGames.adapter = null
        _binding = null
    }
}