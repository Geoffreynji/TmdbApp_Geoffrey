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

data class TmdbSeriesDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val created_by: List<Any>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<GenreSerie>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val name: String,
    val networks: List<Network>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanySerie>,
    val production_countries: List<ProductionCountrySerie>,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguageSerie>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)

data class GenreSerie(
    val id: Int,
    val name: String
)

data class Network(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class ProductionCompanySerie(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class ProductionCountrySerie(
    val iso_3166_1: String,
    val name: String
)

data class Season(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int,
    val vote_average: Double
)

data class SpokenLanguageSerie(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)

data class TmdbMovieCreditsResult(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)

data class Crew(
    val adult: Boolean,
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)

data class TmdbSerieCreditsResult(
    val cast: List<CastSerie>,
    val crew: List<CrewSerie>,
    val id: Int
)

data class CastSerie(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)

data class CrewSerie(
    val adult: Boolean,
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)