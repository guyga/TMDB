package com.example.tmdb.view.details

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
import com.example.tmdb.view.ApiStatus


@BindingAdapter("detailsImage")
fun bindDetailsImage(imageView: ImageView, posterUrl: String?) {
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

@BindingAdapter("budget")
fun bindBudget(textView: TextView, details: MovieDetails?) {
    details?.let {
        textView.text = textView.resources.getString(R.string.detail_budget, it.movieDetails.budget)
    }
}

@BindingAdapter("release")
fun bindRelease(textView: TextView, details: MovieDetails?) {
    details?.let {
        textView.text =
            textView.resources.getString(R.string.detail_release, it.movieDetails.release_date)
    }
}

@BindingAdapter("runtime")
fun bindRuntime(textView: TextView, details: MovieDetails?) {
    details?.let {
        textView.text =
            textView.resources.getString(R.string.detail_runtime, it.movieDetails.runtime)
    }
}

@BindingAdapter("rating")
fun bindRating(textView: TextView, details: MovieDetails?) {
    details?.let {
        val rating: Float = it.movieDetails.vote_average
        val ratingString = String.format("${rating * 10} /10")
        textView.text = textView.resources.getString(R.string.detail_rating, ratingString)
    }
}

@BindingAdapter("overview")
fun bindOverview(textView: TextView, details: MovieDetails?) {
    details?.let {
        textView.text = details.movieDetails.overview
    }
}

@BindingAdapter("status")
fun bindStatus(textView: TextView, details: MovieDetails?) {
    details?.let {
        textView.text = textView.resources.getString(R.string.detail_status, it.movieDetails.status)
    }
}

@BindingAdapter("detailsLoadingStatus")
fun bindFeedLoadingStatus(progressBar: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.LOADING ->
            progressBar.visibility = View.VISIBLE
        else ->
            progressBar.visibility = View.GONE
    }
}

@BindingAdapter("detailsError")
fun bindFeedErrorMessage(textView: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.ERROR ->
            textView.visibility = View.VISIBLE
        else ->
            textView.visibility = View.GONE
    }
}

@BindingAdapter("detailsLayoutVisibility")
fun bindDetailsLayoutVisibility(view: View, apiStatus: ApiStatus) {
    when (apiStatus) {
        ApiStatus.DONE ->
            view.visibility = View.VISIBLE
        else ->
            view.visibility = View.GONE
    }
}

@BindingAdapter("cast")
fun bindCastMembers(recyclerView: RecyclerView, details: MovieDetails?) {
    details?.let {
        (recyclerView.adapter as CastAdapter).submitList(it.credits.cast)
    }
}

@BindingAdapter("castMemberPicture")
fun bindCastMemberPicture(imageView: ImageView, pictureUrl: String?) {
    pictureUrl?.let {
        Glide.with(imageView)
            .load(pictureUrl)
            .apply(
                RequestOptions()
                    .transform(CenterCrop(), RoundedCorners(16))
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}

@BindingAdapter("castMemberName")
fun bindCastMemberName(textView: TextView, name: String?) {
    textView.text = name
}

@BindingAdapter("videos")
fun bindVideos(recyclerView: RecyclerView, details: MovieDetails?) {
    details?.let {
        (recyclerView.adapter as VideosAdapter).submitList(it.videos.results)
    }
}

@BindingAdapter("video")
fun bindVideo(imageView: ImageView, imgPath: String) {
    imgPath?.let {
        Glide.with(imageView)
            .load(imgPath)
            .into(imageView)
    }
}