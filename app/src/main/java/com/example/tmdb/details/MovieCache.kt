package com.example.tmdb.details

import com.example.tmdb.network.entities.MovieCreditsResponse
import com.example.tmdb.network.entities.MovieResponse
import com.example.tmdb.network.entities.MovieVideoResponse

/**
 * Cached data for a single movie
 */
class MovieCache() {
    private val _movieDetails = HashMap<Int, MovieResponse>()
    private val _movieCredits = HashMap<Int, MovieCreditsResponse>()
    private val _movieVideos = HashMap<Int, MovieVideoResponse>()

    fun update(movieId: Int, movieResponse: MovieResponse) {
        _movieDetails[movieId] = movieResponse
    }

    fun update(movieId: Int, movieCredits: MovieCreditsResponse) {
        _movieCredits[movieId] = movieCredits
    }

    fun update(movieId: Int, movieVideos: MovieVideoResponse) {
        _movieVideos[movieId] = movieVideos
    }

    fun getDetails(movieId: Int): MovieResponse? {
        return _movieDetails[movieId]
    }

    fun getCredits(movieId: Int): MovieCreditsResponse? {
        return _movieCredits[movieId]
    }

    fun getVideos(movieId: Int): MovieVideoResponse? {
        return _movieVideos[movieId]
    }

}