package com.example.premiereapplication.ui.theme

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String): TmdbMovieResult

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("query") query: String  // Le titre du film à rechercher
    ): TmdbMovieResult

    // Requête pour récupérer les séries TV en tendance
    @GET("trending/tv/week")
    suspend fun trendingSeries(@Query("api_key") api_key: String): TmdbSeriesResult

    @GET("trending/person/week")  // Endpoint pour récupérer les acteurs
    suspend fun getTrendingActors(@Query("api_key") api_key: String): TmdbActorResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int,
                                @Query("api_key") apiKey: String
    ): TmdbMoviesDetails
}
