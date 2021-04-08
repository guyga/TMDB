package com.example.tmdb.view.details

import com.example.tmdb.network.entities.MovieCreditsResponse
import com.example.tmdb.network.entities.MovieResponse
import com.example.tmdb.network.entities.MovieVideoResponse

class MovieDetails(
    val movieDetails: MovieResponse,
    val credits: MovieCreditsResponse,
    val videos: MovieVideoResponse
) {

}