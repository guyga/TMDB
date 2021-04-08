package com.example.tmdb.network.entities

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500"

data class CastMember(
    val name: String,
    val profile_path: String
) {

    fun getPicturePath() = BASE_POSTER_PATH + profile_path
}