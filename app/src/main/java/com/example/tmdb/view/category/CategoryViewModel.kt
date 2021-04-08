package com.example.tmdb.view.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.network.entities.DiscoverMovieResponse
import com.example.tmdb.network.entities.Movie
import com.example.tmdb.view.ApiStatus
import com.example.tmdb.view.Category
import kotlinx.coroutines.launch

class CategoryViewModel() : ViewModel() {

    private lateinit var _category: Category
    private val _allResponses = ArrayList<DiscoverMovieResponse>()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    // navigation
    private val _navigateToMovieDetails = MutableLiveData<Movie?>()
    val navigateToMovieDetails: LiveData<Movie?>
        get() = _navigateToMovieDetails

    fun fetchCategory(category: Category, page: Int = 1) {
        _category = category
        viewModelScope.launch {
            _apiStatus.value = ApiStatus.LOADING
            try {
                val response = category.fetch(page)
                _allResponses.add(response)
                _apiStatus.value = ApiStatus.DONE
                _movies.value = getAllMovies()
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                _movies.value = ArrayList()
            }
        }
    }

    private fun getAllMovies(): List<Movie> {
        var movies = ArrayList<Movie>()
        for (response in _allResponses) {
            movies.addAll(response.results)
        }
        return movies
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToMovieDetails.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToMovieDetails.value = null
    }

    fun onCategoryBottomReached() {
        if (canLoadMore()) {
            fetchCategory(_category, _allResponses[_allResponses.size - 1].page + 1)
        }
    }

    private fun canLoadMore(): Boolean {
        return _allResponses.size > 0 && _allResponses[_allResponses.size - 1].page < _allResponses[_allResponses.size - 1].total_pages
    }

}