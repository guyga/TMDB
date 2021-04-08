package com.example.tmdb.network

import com.example.tmdb.BuildConfig
import com.example.tmdb.network.entities.DiscoverMovieResponse
import com.example.tmdb.network.entities.MovieCreditsResponse
import com.example.tmdb.network.entities.MovieResponse
import com.example.tmdb.network.entities.MovieVideoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface MoviesApiService {
    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getNowPlayingMovies(
        @Query("primary_release_date.gte") fromDate: String,
        @Query("primary_release_date.lte") toDate: String,
        @Query("page") page: Int
    ): DiscoverMovieResponse

    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&sort_by=popularity.desc")
    suspend fun getPopularMovies(@Query("page") page: Int): DiscoverMovieResponse

    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&sort_by=vote_average.desc")
    suspend fun getTopRatedMovies(@Query("page") page: Int): DiscoverMovieResponse

    @GET("movie/upcoming?api_key=${BuildConfig.TMDB_API_KEY}&sort_by=vote_average.desc")
    suspend fun getUpcomingMovies(@Query("page") page: Int): DiscoverMovieResponse
}

interface MovieApiService {
    @GET("movie/{movieId}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovie(@Path("movieId") movieId: Int): MovieResponse

    @GET("movie/{movieId}/credits?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): MovieCreditsResponse

    @GET("movie/{movieId}/videos?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun getMovieVideos(@Path("movieId") movieId: Int): MovieVideoResponse
}

object MoviesApi {
    val moviesService: MoviesApiService by lazy { retrofit.create(MoviesApiService::class.java) }
    val movieService: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}