package com.example.premiereapplication.ui.theme

data class TmdbMovieResult(
    val page: Int,
    val results: List<TmdbMovie>
)

data class TmdbSeriesResult(
    val page: Int,
    val results: List<TmdbSeries>
)

data class TmdbActorResult(
    val results: List<TmdbActor>
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

data class TmdbSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?  // Utilise ? pour gérer les cas où le chemin de l'affiche peut être nul
)

data class TmdbActor(
    val id: Int,
    val name: String,
    val profile_path: String?  // Chemin vers l'image de profil
)

data class TmdbMoviesDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)