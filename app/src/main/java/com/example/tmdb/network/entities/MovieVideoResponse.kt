package com.example.tmdb.network.entities

data class MovieVideoResponse(
    val id: Int,
    val results: List<Video>
) {
}