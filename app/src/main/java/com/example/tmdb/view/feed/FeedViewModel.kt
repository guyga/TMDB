package com.example.tmdb.view.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.network.entities.Movie
import com.example.tmdb.view.ApiStatus
import com.example.tmdb.view.Category
import kotlinx.coroutines.launch

class FeedViewModel() : ViewModel() {

    // Now Playing movies
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _apiStatusNowPlaying = MutableLiveData<ApiStatus>()
    val apiStatusNowPlaying: LiveData<ApiStatus>
        get() = _apiStatusNowPlaying

    // Popular movies
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _apiStatusPopular = MutableLiveData<ApiStatus>()
    val apiStatusPopular: LiveData<ApiStatus>
        get() = _apiStatusPopular

    // top rated movies
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies

    private val _apiStatusTopRated = MutableLiveData<ApiStatus>()
    val apiStatusTopRated: LiveData<ApiStatus>
        get() = _apiStatusTopRated

    // upcoming movies
    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>>
        get() = _upcomingMovies

    private val _apiStatusUpcoming = MutableLiveData<ApiStatus>()
    val apiStatusUpcoming: LiveData<ApiStatus>
        get() = _apiStatusUpcoming

    // navigation
    private val _navigateToMovieDetails = MutableLiveData<Movie?>()
    val navigateToMovieDetails: LiveData<Movie?>
        get() = _navigateToMovieDetails

    private val _navigateToCategory = MutableLiveData<Int>()
    val navigateToCategory: LiveData<Int>
        get() = _navigateToCategory

    init {
        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _apiStatusNowPlaying.value = ApiStatus.LOADING
            try {
                val nowPlayingMovies = Category.NOW_PLAYING.fetch(1)
                _apiStatusNowPlaying.value = ApiStatus.DONE
                _nowPlayingMovies.value = nowPlayingMovies.results
            } catch (e: Exception) {
                e.printStackTrace()
                _apiStatusNowPlaying.value = ApiStatus.ERROR
                _nowPlayingMovies.value = ArrayList()
            }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _apiStatusPopular.value = ApiStatus.LOADING
            try {
                val popularMovies = Category.POPULAR_MOVIES.fetch(1)
                _apiStatusPopular.value = ApiStatus.DONE
                _popularMovies.value = popularMovies.results
            } catch (e: Exception) {
                _apiStatusPopular.value = ApiStatus.ERROR
                _popularMovies.value = ArrayList()
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _apiStatusTopRated.value = ApiStatus.LOADING
            try {
                val topRatedMovies = Category.TOP_RATED_MOVIES.fetch(1)
                _apiStatusTopRated.value = ApiStatus.DONE
                _topRatedMovies.value = topRatedMovies.results
            } catch (e: Exception) {
                _apiStatusTopRated.value = ApiStatus.ERROR
                _topRatedMovies.value = ArrayList()
            }
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch {
            _apiStatusUpcoming.value = ApiStatus.LOADING
            try {
                val upcomingMovies = Category.UPCOMING_MOVIES.fetch(1)
                _apiStatusUpcoming.value = ApiStatus.DONE
                _upcomingMovies.value = upcomingMovies.results
            } catch (e: Exception) {
                _apiStatusUpcoming.value = ApiStatus.ERROR
                _upcomingMovies.value = ArrayList()
            }
        }
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToMovieDetails.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToMovieDetails.value = null
    }

    fun displayCategory(category: Int) {
        _navigateToCategory.value = category
    }

    fun displayCategoryComplete() {
        _navigateToCategory.value = -1
    }
}