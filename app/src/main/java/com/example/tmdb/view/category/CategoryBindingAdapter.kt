package com.example.tmdb.view.category

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb.R
import com.example.tmdb.network.entities.Movie
import com.example.tmdb.view.ApiStatus

@BindingAdapter("categoryMovie")
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

@BindingAdapter("categoryData")
fun bindCategoryMovies(recyclerView: RecyclerView, movies: List<Movie>?) {
    (recyclerView.adapter as CategoryAdapter).submitList(movies)
}


@BindingAdapter("categoryListVisibility")
fun bindCategoryListVisibility(view: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.ERROR -> {
            view.visibility = View.GONE
        }
        else -> {
            view.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("categoryError")
fun bindCategoryErrorVisibility(view: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.ERROR -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("categoryLoadingStatus")
fun bindCategoryLoadingVisibility(view: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.LOADING -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}