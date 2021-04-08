package com.example.tmdb.feed

import com.example.tmdb.network.entities.DiscoverMovieResponse
import com.example.tmdb.network.MoviesApi
import com.example.tmdb.view.Category
import java.text.SimpleDateFormat
import java.util.*

class FeedRepository private constructor() {

    private var _dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private var _feedCache = FeedCache()

    suspend fun getNowPlayingMovies(page: Int): DiscoverMovieResponse {
        val cached = _feedCache.get(Category.NOW_PLAYING.ordinal, page)
        return if (cached != null)
            cached
        else {
            val toDate = Calendar.getInstance()
            val fromDate = Calendar.getInstance()
            fromDate.add(Calendar.DAY_OF_MONTH, -3)

            val response = MoviesApi.moviesService.getNowPlayingMovies(
                _dateFormat.format(fromDate.time),
                _dateFormat.format(toDate.time),
                page
            )
            _feedCache.update(Category.NOW_PLAYING.ordinal, page, response)
            response
        }
    }

    suspend fun getPopularMovies(page: Int): DiscoverMovieResponse {
        val cached = _feedCache.get(Category.POPULAR_MOVIES.ordinal, page)
        return if (cached != null)
            cached
        else {
            val response = MoviesApi.moviesService.getPopularMovies(page)
            _feedCache.update(Category.POPULAR_MOVIES.ordinal, page, response)
            return response
        }
    }

    suspend fun getTopRatedMovies(page: Int): DiscoverMovieResponse {
        val cached = _feedCache.get(Category.TOP_RATED_MOVIES.ordinal, page)
        return if (cached != null)
            cached
        else {
            val response = MoviesApi.moviesService.getTopRatedMovies(page)
            _feedCache.update(Category.TOP_RATED_MOVIES.ordinal, page, response)
            return response
        }
    }

    suspend fun getUpcomingMovies(page: Int): DiscoverMovieResponse {
        val cached = _feedCache.get(Category.UPCOMING_MOVIES.ordinal, page)
        return if (cached != null)
            cached
        else {
            val response = MoviesApi.moviesService.getUpcomingMovies(page)
            _feedCache.update(Category.UPCOMING_MOVIES.ordinal, page, response)
            return response
        }
    }

    companion object FeedRepo {
        val feedRepo: FeedRepository by lazy { FeedRepository() }
    }
}