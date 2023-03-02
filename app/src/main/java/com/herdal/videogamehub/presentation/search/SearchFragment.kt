package com.herdal.videogamehub.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.herdal.videogamehub.databinding.FragmentSearchBinding
import com.herdal.videogamehub.presentation.home.adapter.GameAdapter
import com.herdal.videogamehub.utils.ext.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchGamesAdapter: GameAdapter by lazy {
        GameAdapter(::onGameClick)
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        setupSearchView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.apply {
        rvSearchedGames.adapter = searchGamesAdapter
    }

    private fun searchGames(searchText: String) = binding.apply {
        viewModel.searchGames(searchText)
        collectLatestLifecycleFlow(viewModel.searchedGames) { pagingData ->
            searchGamesAdapter.submitData(pagingData)
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

    private fun onGameClick(gameId: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}