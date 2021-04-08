package com.example.tmdb.view

import com.example.tmdb.R
import com.example.tmdb.feed.FeedRepository
import com.example.tmdb.network.entities.DiscoverMovieResponse

enum class Category(
    val titleRes: Int,
    val fetch: suspend (Int) -> DiscoverMovieResponse
) {
    NOW_PLAYING(
        R.string.title_now_playing,
        { page: Int -> FeedRepository.feedRepo.getNowPlayingMovies(page) }),
    POPULAR_MOVIES(
        R.string.title_popular_movies,
        { page: Int -> FeedRepository.feedRepo.getPopularMovies(page) }),
    TOP_RATED_MOVIES(
        R.string.title_top_rated_movies,
        { page: Int -> FeedRepository.feedRepo.getTopRatedMovies(page) }),
    UPCOMING_MOVIES(
        R.string.title_upcoming_movies,
        { page: Int -> FeedRepository.feedRepo.getUpcomingMovies(page) })
}