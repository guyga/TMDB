package com.example.tmdb.view.details

import androidx.lifecycle.*
import com.example.tmdb.details.MovieRepository
import com.example.tmdb.network.entities.MovieCreditsResponse
import com.example.tmdb.network.entities.MovieResponse
import com.example.tmdb.network.entities.MovieVideoResponse
import com.example.tmdb.view.ApiStatus
import kotlinx.coroutines.launch

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    // movie details
    private val _movieDetails = MutableLiveData<MovieResponse>()
    private val _movieDetailsApiStatus = MutableLiveData<ApiStatus>()

    // movie credits
    private val _movieCredits = MutableLiveData<MovieCreditsResponse>()
    private val _movieCreditsApiStatus = MutableLiveData<ApiStatus>()

    // movie videos
    private val _movieVideos = MutableLiveData<MovieVideoResponse>()
    private val _movieVideosApiStatus = MutableLiveData<ApiStatus>()

    // movie details wrapper
    lateinit var _movieDetailsWrapper: LiveData<MovieDetails?>
    lateinit var _movieDetailsWrapperApiStatus: LiveData<ApiStatus>


    init {
        val movieDetailsWrapperApiStatus = MediatorLiveData<ApiStatus>()
        movieDetailsWrapperApiStatus.value = ApiStatus.LOADING

        movieDetailsWrapperApiStatus.addSource(_movieDetailsApiStatus) {
            movieDetailsWrapperApiStatus.value = it
        }
        movieDetailsWrapperApiStatus.addSource(_movieCreditsApiStatus) {
            movieDetailsWrapperApiStatus.value = it
        }
        movieDetailsWrapperApiStatus.addSource(_movieVideosApiStatus) {
            movieDetailsWrapperApiStatus.value = it
        }

        _movieDetailsWrapperApiStatus = Transformations.map(movieDetailsWrapperApiStatus) {
            val detailsStatus: ApiStatus? = _movieDetailsApiStatus.value
            val creditsStatus: ApiStatus? = _movieCreditsApiStatus.value
            val videosStatus: ApiStatus? = _movieVideosApiStatus.value
            if (detailsStatus != null && creditsStatus != null && videosStatus != null) {
                if (detailsStatus == ApiStatus.ERROR || creditsStatus == ApiStatus.ERROR || videosStatus == ApiStatus.ERROR) {
                    return@map ApiStatus.ERROR
                }
                if (detailsStatus == ApiStatus.LOADING || creditsStatus == ApiStatus.LOADING || videosStatus == ApiStatus.LOADING) {
                    return@map ApiStatus.LOADING
                } else {
                    return@map ApiStatus.DONE
                }
            }

            return@map ApiStatus.LOADING
        }

        _movieDetailsWrapper = Transformations.map(_movieDetailsWrapperApiStatus) {
            if (it == ApiStatus.DONE) {
                return@map MovieDetails(
                    _movieDetails.value!!,
                    _movieCredits.value!!,
                    _movieVideos.value!!
                )
            } else {
                return@map null
            }
        }
    }

    fun fetchMovieDataData(movieId: Int) {
        fetchMovieDetails(movieId)
        fetchMovieCredits(movieId)
        fetchMovieVideos(movieId)
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetailsApiStatus.value = ApiStatus.LOADING
            try {
                val movieDetails = movieRepository.getMovieDetails(movieId)
                _movieDetails.value = movieDetails
                _movieDetailsApiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _movieDetailsApiStatus.value = ApiStatus.ERROR
            }
        }
    }

    private fun fetchMovieCredits(movieId: Int) {
        viewModelScope.launch {
            _movieCreditsApiStatus.value = ApiStatus.LOADING
            try {
                val movieCredits = movieRepository.getMovieCredits(movieId)
                _movieCredits.value = movieCredits
                _movieCreditsApiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _movieCreditsApiStatus.value = ApiStatus.ERROR
            }
        }
    }

    private fun fetchMovieVideos(movieId: Int) {
        viewModelScope.launch {
            _movieVideosApiStatus.value = ApiStatus.LOADING
            try {
                val movieVideos = movieRepository.getMovieVideos(movieId)
                _movieVideos.value = movieVideos
                _movieVideosApiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _movieVideosApiStatus.value = ApiStatus.ERROR
            }
        }
    }
}