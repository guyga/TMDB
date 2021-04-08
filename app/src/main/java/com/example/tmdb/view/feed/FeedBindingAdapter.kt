package com.example.tmdb.view.feed

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb.R
import com.example.tmdb.network.entities.Movie
import com.example.tmdb.view.ApiStatus


@BindingAdapter("movies")
fun bindMovies(recyclerView: RecyclerView, data: List<Movie>?) {
    (recyclerView.adapter as FeedAdapter).submitList(data)
}

@BindingAdapter("feedLoadingStatus")
fun bindFeedLoadingStatus(progressBar: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.LOADING ->
            progressBar.visibility = View.VISIBLE
        else ->
            progressBar.visibility = View.GONE
    }
}

@BindingAdapter("feedError")
fun bindFeedErrorMessage(textView: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.ERROR ->
            textView.visibility = View.VISIBLE
        else ->
            textView.visibility = View.GONE
    }
}

@BindingAdapter("moviePosterUrl")
fun bindMoviePoster(imageView: ImageView, posterUrl: String?) {
    posterUrl?.let {
        Glide.with(imageView)
            .load(posterUrl)
            .apply(
                RequestOptions()
                    .transform(CenterCrop(), RoundedCorners(16))
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}

@BindingAdapter("movieTitle")
fun bindMovieTitle(textView: TextView, title: String) {
    textView.text = title
}