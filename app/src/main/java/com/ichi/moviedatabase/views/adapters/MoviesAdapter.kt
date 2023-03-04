package com.ichi.moviedatabase.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ichi.moviedatabase.R
import com.ichi.moviedatabase.core.imageloader.ImageLoaderInterface
import com.ichi.moviedatabase.databinding.ViewHolderMoviesItemBinding
import com.ichi.moviedatabase.di.UnsupportedByHiltComponentDependencies
import com.ichi.moviedatabase.viewmodels.models.AppHomeMovie
import dagger.hilt.android.EntryPointAccessors

class MoviesAdapter : Adapter<MoviesAdapter.ViewHolder>() {
    private var listener: ((movie: AppHomeMovie) -> Unit)? = null
    var movies: List<AppHomeMovie> = emptyList()

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolderMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val movie = movies[position]
            holder.bind(movie, listener)
        }
    }

    fun dispatchUpdates(newList: List<AppHomeMovie>) {
        val oldList = movies
        movies = newList

        val diff = DiffUtil.calculateDiff(PopularMoviesDiffUtil(oldList, movies))
        diff.dispatchUpdatesTo(this)
    }

    fun setOnClickMovieListener(listener: (movie: AppHomeMovie) -> Unit) {
        this.listener = listener
    }

    class ViewHolder(private val viewBinding: ViewHolderMoviesItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(movie: AppHomeMovie, listener: ((movie: AppHomeMovie) -> Unit)?) {
            loadImage(movie)
            viewBinding.root.setOnClickListener { listener?.invoke(movie) }
        }

        private fun loadImage(movie: AppHomeMovie) {
            val appContext = viewBinding.root.context.applicationContext
            val hiltEntryPoint = EntryPointAccessors.fromApplication<UnsupportedByHiltComponentDependencies>(appContext)
            val imageLoader = hiltEntryPoint.getImageLoader()

            val params = ImageLoaderInterface.Parameters(
                errorResId = R.color.teal_700,
                placeholderResId = R.color.teal_700,
            )
            imageLoader.loadImage(viewBinding.posterImageView, movie.posterPath, params)
            viewBinding.posterImageView.contentDescription = viewBinding.root.context.getString(R.string.fragment_movies_poster_content_description, movie.title)
        }
    }
}
