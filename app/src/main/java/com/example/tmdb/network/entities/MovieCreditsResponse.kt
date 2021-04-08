package com.example.tmdb.network.entities

data class MovieCreditsResponse(
    val id: Int,
    val cast: List<CastMember>
) {
}