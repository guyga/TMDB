package com.example.tmdb.feed

import com.example.tmdb.network.entities.DiscoverMovieResponse

/**
 * Cached data for all movie categories
 */
class FeedCache {
    /**
     * mapping between a category id (based on its ordinal value at - [com.example.tmdb.view.Category])
     * to a mapping between that category's pages and their content
     */
    private val _cache = HashMap<Int, HashMap<Int, DiscoverMovieResponse>>()

    fun update(categoryId: Int, pageNum: Int, response: DiscoverMovieResponse) {
        var categoryCache = _cache[categoryId]
        if (categoryCache == null)
            categoryCache = HashMap()

        categoryCache[pageNum] = response
        _cache[categoryId] = categoryCache
    }

    fun get(categoryId: Int, pageNum: Int): DiscoverMovieResponse? {
        val categoryCache = _cache[categoryId]
        return if (categoryCache != null)
            categoryCache[pageNum]
        else null
    }
}