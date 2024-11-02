package com.example.premiereapplication.ui.theme


import android.graphics.Movie
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val series = MutableStateFlow<List<TmdbSeries>>(listOf())
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    val movieDetails = MutableStateFlow<TmdbMoviesDetails?>(null)

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
                val result = api.searchMovies(apiKey, query)  // Appel réseau pour rechercher le film par titre
                movies.value = result.results                 // Mettre à jour l'état avec les résultats de la recherche
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

    fun getActors() {
        viewModelScope.launch {
            try {
                val result = api.getTrendingActors(apiKey)  // Méthode d'API pour récupérer les acteurs
                actors.value = result.results // Vérifie que result.results est une List<TmdbActor>
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
}
