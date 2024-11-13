package com.example.premiereapplication.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    //Définition de mutablestateflow, permet de stocker un état réactif et observable
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val series = MutableStateFlow<List<TmdbSeries>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    val movieDetails = MutableStateFlow<TmdbMoviesDetails?>(null)
    val seriesDetails = MutableStateFlow<TmdbSeriesDetails?>(null)
    val movieCast = MutableStateFlow<List<Cast>>(emptyList())
    val seriesActors = MutableStateFlow<List<CastSerie>>(emptyList())
    val collections = MutableStateFlow<List<Result>>(emptyList())


    // Initialiser Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    private val apiKey = "b57151d36fecd1b693da830a2bc5766f"

    val api = retrofit.create(Api::class.java)

    fun getFilmsInitiaux() {
        viewModelScope.launch {
            try {
                val result = api.lastmovies(apiKey)  // Appel réseau
                movies.value = result.results        // Extraire la liste des films
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchFilms(query: String) {
        viewModelScope.launch {
            try {
                val result = api.searchMovies(
                    apiKey,
                    query
                )  // Appel réseau pour rechercher le film par titre
                movies.value =
                    result.results                 // Mettre à jour l'état avec les résultats de la recherche
            } catch (e: Exception) {
                e.printStackTrace()  // Gestion des erreurs
            }
        }
    }

    // Récupérer les séries TV
    fun getSeries() {
        viewModelScope.launch {
            try {
                val result = api.trendingSeries(apiKey)
                series.value = result.results  // Met à jour la liste des séries
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchSeries(query: String) {
        viewModelScope.launch {
            try {
                val result = api.searchSeries(
                    apiKey,
                    query
                )  // Appel réseau pour rechercher la série par titre
                series.value =
                    result.results                    // Mettre à jour l'état avec les résultats de la recherche
            } catch (e: Exception) {
                e.printStackTrace()  // Gestion des erreurs
            }
        }
    }

    fun getActors() {
        viewModelScope.launch {
            try {
                val result =
                    api.getTrendingActors(apiKey)  // Méthode d'API pour récupérer les acteurs
                actors.value = result.results // Vérifie que result.results est une List<TmdbActor>
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchActors(query: String) {
        viewModelScope.launch {
            try {
                val result = api.searchActors(
                    apiKey,
                    query
                )  // Appel réseau pour rechercher l'acteur par nom
                actors.value =
                    result.results                   // Mettre à jour l'état avec les résultats de la recherche
            } catch (e: Exception) {
                e.printStackTrace()  // Gestion des erreurs
            }
        }
    }

    //Récupérer les détails spécifiques d'un film
    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = api.getMovieDetails(movieId, apiKey)
                movieDetails.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Récupérer les détails des séries
    fun getSeriesDetails(serieId: Int) {
        viewModelScope.launch {
            try {
                val result = api.getSeriesDetails(
                    serieId,
                    apiKey
                )
                seriesDetails.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Récupérer acteurs qui ont joué dans un film spécifique (movieId)
    fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = api.getMovieCredits(movieId, apiKey)
                movieCast.value = result.cast
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Récupérer acteurs qui ont joué dans un film spécifique (seriesId)
    fun getSeriesCast(seriesId: Int) {
        viewModelScope.launch {
            try {
                val response = api.getSeriesCredits(seriesId, apiKey)
                seriesActors.value = response.cast
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Partie exam
    // Méthode pour rechercher des collections par mot-clé
    fun searchCollections(query: String) {
        viewModelScope.launch {
            try {
                val result = api.searchCollections(
                    apiKey,
                    query
                )
            collections.value = result.results// Appel réseau pour rechercher l'acteur par nom
            } catch (e: Exception) {
                e.printStackTrace()  // Gestion des erreurs
            }
        }
    }
}
