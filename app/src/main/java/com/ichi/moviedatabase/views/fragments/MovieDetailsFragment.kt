package com.ichi.moviedatabase.views.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ichi.moviedatabase.R
import com.ichi.moviedatabase.core.extensions.repeatOnLifecycleStarted
import com.ichi.moviedatabase.core.imageloader.ImageLoaderInterface
import com.ichi.moviedatabase.databinding.FragmentMovieDetailsBinding
import com.ichi.moviedatabase.viewmodels.MovieDetailsViewModel
import com.ichi.moviedatabase.viewmodels.models.AppMovie
import com.ichi.moviedatabase.views.widgets.ListContainerWidget
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    @Inject lateinit var imageLoader: ImageLoaderInterface

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding = FragmentMovieDetailsBinding::inflate

    override fun onViewCreated() {
        super.onViewCreated()
        setupObservers()
        checkMovie()
    }

    private fun setupObservers() {
        setupMovieObserver()
        setupLoaderObserver()
        setupErrorObserver()
    }

    private fun setupMovieObserver() {
        repeatOnLifecycleStarted {
            movieDetailsViewModel.movie.collect { movie ->
                if (movie != null) {
                    viewBinding.movieDetailsScrollView.isVisible = true
                    updateUI(movie)
                }
            }
        }
    }

    private fun setupLoaderObserver() {
        repeatOnLifecycleStarted {
            movieDetailsViewModel.showLoader.collect { showLoader ->
                viewBinding.progressBar.isVisible = showLoader
            }
        }
    }

    private fun setupErrorObserver() {
        repeatOnLifecycleStarted {
            movieDetailsViewModel.error.collect { errorResId ->
                errorResId?.let {
                    viewBinding.noMovieDetailsTextView.isVisible = true
                    showErrorDialog(errorResId)
                }
            }
        }
    }

    private fun updateUI(movie: AppMovie) {
        viewBinding.titleTextView.text = movie.title
        updateMovieStatus(movie)
        imageLoader.loadImage(viewBinding.backdropImageView, movie.backdropPath)
        viewBinding.overviewTextView.text = movie.overview
        updateSimpleListContainer(movie.genres, viewBinding.genresContainerWidget, R.string.fragment_movie_details_genres_title)
        updateSimpleListContainer(movie.productionCompanies, viewBinding.productionCompaniesContainerWidget, R.string.fragment_movie_details_production_companies_title)
        updateBoxOffice(movie)
        updateSimpleListContainer(movie.spokenLanguages, viewBinding.spokenLanguagesContainerWidget, R.string.fragment_movie_details_spoken_languages_title)
        updateRating(movie)
    }

    private fun updateMovieStatus(movie: AppMovie) {
        val status = when (movie.status) {
            AppMovie.Status.UNKNOWN -> getString(R.string.na)
            AppMovie.Status.RUMORED -> getString(R.string.fragment_movie_details_rumored_status)
            AppMovie.Status.PLANNED -> getString(R.string.fragment_movie_details_planned_status)
            AppMovie.Status.IN_PRODUCTION -> getString(R.string.fragment_movie_details_in_production_status)
            AppMovie.Status.POST_PRODUCTION -> getString(R.string.fragment_movie_details_post_production_status)
            AppMovie.Status.RELEASED -> getString(R.string.fragment_movie_details_released_status)
            AppMovie.Status.CANCELED -> getString(R.string.fragment_movie_details_canceled_status)
        }

        val runtime = when {
            movie.runtime.isEmpty() -> getString(R.string.na)
            movie.runtime.hours.isEmpty() -> getString(R.string.fragment_movie_details_runtime_without_hours, movie.runtime.minutes)
            else -> getString(R.string.fragment_movie_details_runtime_with_hours, movie.runtime.hours, movie.runtime.minutes)
        }

        viewBinding.dateLengthTextView.text = getString(R.string.fragment_movie_details_movie_status, status, movie.releaseDate, runtime)
    }

    private fun updateSimpleListContainer(items: List<String>, container: ListContainerWidget, @StringRes title: Int) {
        if (items.isEmpty()) {
            container.isVisible = false
        } else {
            container.init(title, items)
        }
    }

    private fun updateBoxOffice(movie: AppMovie) {
        val boxOfficeItems = mutableListOf<String>()

        if (movie.budget.isNotBlank()) {
            val budget = getString(R.string.fragment_movie_details_box_office_budget, movie.budget)
            boxOfficeItems.add(budget)
        }

        if (movie.revenue.isNotBlank()) {
            val revenue = getString(R.string.fragment_movie_details_box_office_revenue, movie.revenue)
            boxOfficeItems.add(revenue)
        }

        if (boxOfficeItems.isEmpty()) {
            viewBinding.boxOfficeContainerWidget.isVisible = false
        } else {
            viewBinding.boxOfficeContainerWidget.init(R.string.fragment_movie_details_box_office_title, boxOfficeItems)
        }
    }

    private fun updateRating(movie: AppMovie) {
        val ratings = mutableListOf<String>()

        if (movie.averageRating.isNotBlank()) {
            val rating = getString(R.string.fragment_movie_details_rating, movie.averageRating)
            ratings.add(rating)
        }

        if (movie.ratings != 0) {
            val ratingCount = getString(R.string.fragment_movie_details_ratings, movie.ratings)
            ratings.add(ratingCount)
        }

        if (ratings.isEmpty()) {
            viewBinding.ratingContainerWidget.isVisible = false
        } else {
            viewBinding.ratingContainerWidget.init(R.string.fragment_movie_details_rating_title, ratings)
        }
    }

    private fun checkMovie() {
        // If the movie is not null the fragment has been recreated so we don't query it again
        if (movieDetailsViewModel.movie.value == null) {
            movieDetailsViewModel.getMovie(args.movieId)
        }
    }
}
