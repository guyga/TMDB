package com.example.tmdb.network.entities

data class MovieResponse(
    val budget: Int,
    val release_date: String,
    val runtime: Int,
    val vote_average: Float,
    val status: String,
    val overview: String
) {
}