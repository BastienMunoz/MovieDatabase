package com.ichi.moviedatabase.views.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ichi.moviedatabase.core.extensions.repeatOnLifecycleStarted
import com.ichi.moviedatabase.databinding.FragmentMoviesBinding
import com.ichi.moviedatabase.viewmodels.MoviesViewModel
import com.ichi.moviedatabase.viewmodels.models.AppHomeMovie
import com.ichi.moviedatabase.views.adapters.MoviesAdapter
import com.ichi.moviedatabase.views.listeners.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val gridRows = 2
    private val moviesViewModel: MoviesViewModel by viewModels()

    private lateinit var adapter: MoviesAdapter
    private lateinit var layoutManager: GridLayoutManager

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesBinding = FragmentMoviesBinding::inflate

    override fun onViewCreated() {
        super.onViewCreated()
        setRecyclerView()
        setListeners()
        checkMovies()
    }

    private fun setRecyclerView() {
        adapter = MoviesAdapter()
        layoutManager = GridLayoutManager(context, gridRows)
        viewBinding.moviesRecyclerView.layoutManager = layoutManager
        viewBinding.moviesRecyclerView.adapter = adapter
    }

    private fun setListeners() {
        setupOnScrollListener()
        setupOnRefreshListener()
        setupOnClickMovieListener()
        setupPopularMoviesObserver()
        setupLoaderObserver()
        setupErrorObserver()
    }

    private fun setupOnScrollListener() {
        viewBinding.moviesRecyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView) {
                moviesViewModel.getPopularMovies()
            }
        })
    }

    private fun setupOnRefreshListener() {
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            adapter.dispatchUpdates(emptyList())
            moviesViewModel.resetMovies()
        }
    }

    private fun setupOnClickMovieListener() {
        adapter.setOnClickMovieListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
            findNavController().navigate(action)
        }
    }

    private fun setupPopularMoviesObserver() {
        repeatOnLifecycleStarted {
            moviesViewModel.popularMovies.collect {
                updateMovies(it)
            }
        }
    }

    private fun updateMovies(movies: List<AppHomeMovie>) {
        adapter.dispatchUpdates(movies)
        viewBinding.swipeRefreshLayout.isRefreshing = false

        if (moviesViewModel.popularMovies.value.isEmpty()) {
            showNoMoviesTextView()
        } else {
            showMoviesRecyclerView()
        }
    }

    private fun showMoviesRecyclerView() {
        viewBinding.moviesRecyclerView.isVisible = true
        viewBinding.noMoviesTextView.isVisible = false
    }

    private fun showNoMoviesTextView() {
        viewBinding.moviesRecyclerView.isVisible = false
        viewBinding.noMoviesTextView.isVisible = true
    }

    private fun setupLoaderObserver() {
        repeatOnLifecycleStarted {
            moviesViewModel.showLoader.collect {
                viewBinding.progressBar.isVisible = it
            }
        }
    }

    private fun setupErrorObserver() {
        repeatOnLifecycleStarted {
            moviesViewModel.error.collect { errorResId ->
                errorResId?.let { showErrorDialog(it) }
            }
        }
    }

    private fun checkMovies() {
        // If we have movies the fragment has been recreated so we don't need to query
        if (moviesViewModel.popularMovies.value.isEmpty()) {
            moviesViewModel.getPopularMovies()
        }
    }
}
