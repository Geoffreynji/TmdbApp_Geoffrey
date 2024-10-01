package com.example.premiereapplication.ui.theme

data class TmdbMovieResult(
    val page: Int,
    val results: List<TmdbMovie>
)

data class TmdbMovie(
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String
)