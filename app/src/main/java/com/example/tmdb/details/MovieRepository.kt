package com.example.tmdb.details

import com.example.tmdb.network.MoviesApi
import com.example.tmdb.network.entities.MovieCreditsResponse
import com.example.tmdb.network.entities.MovieResponse
import com.example.tmdb.network.entities.MovieVideoResponse

/**
 * Repository for a single movie data
 */
class MovieRepository private constructor() {

    private var _movieCache = MovieCache()

    suspend fun getMovieDetails(movieId: Int): MovieResponse {
        val cached = _movieCache.getDetails(movieId)
        return if (cached != null)
            cached
        else {
            val response = MoviesApi.movieService.getMovie(movieId)
            _movieCache.update(movieId, response)
            response
        }
    }

    suspend fun getMovieCredits(movieId: Int): MovieCreditsResponse {
        val cached = _movieCache.getCredits(movieId)
        return if (cached != null)
            cached
        else {
            val response = MoviesApi.movieService.getMovieCredits(movieId)
            _movieCache.update(movieId, response)
            return response
        }
    }

    suspend fun getMovieVideos(movieId: Int): MovieVideoResponse {
        val cached = _movieCache.getVideos(movieId)
        return if (cached != null)
            cached
        else {
            val response = MoviesApi.movieService.getMovieVideos(movieId)
            _movieCache.update(movieId, response)
            return response
        }
    }

    companion object MovieRepo {
        val movieRepo: MovieRepository by lazy { MovieRepository() }
    }
}