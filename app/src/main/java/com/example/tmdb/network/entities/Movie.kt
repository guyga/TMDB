package com.example.tmdb.network.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500"

@Parcelize
data class Movie(
    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Float,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Float
) : Parcelable {
    fun getPreviewPath(): String {
        var path = backdrop_path
        if (path == null)
            path = poster_path
        return BASE_POSTER_PATH + path
    }

    fun getFullPosterPath(): String = BASE_POSTER_PATH + poster_path

}