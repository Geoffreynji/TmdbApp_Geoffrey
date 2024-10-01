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
}
