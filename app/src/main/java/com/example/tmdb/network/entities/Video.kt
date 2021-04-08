package com.example.tmdb.network.entities

private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
) {
    fun getYoutubeThumbnailPath(): String = "$YOUTUBE_THUMBNAIL_URL$key/default.jpg"
    fun getYouTubeVideoUrl(): String = "$YOUTUBE_VIDEO_URL$key"
}