package com.example.premiereapplication.ui.theme

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Films(viewModel: MainViewModel) {

    // Collecter les films à partir du ViewModel avec collecte de l'état
    val moviesState = viewModel.movies.collectAsState()

    // Vérifie si la liste est vide
    LaunchedEffect(moviesState.value) {
        if (moviesState.value.isEmpty()) {
            viewModel.getFilmsInitiaux()
        }
    }

    // Affichage de la liste des films
    if (moviesState.value.isEmpty()) {
        Text(text = "Aucun film disponible.")
    }
    else
    {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Nombre de colonnes
            modifier = Modifier.padding(16.dp) // Ajout de padding
        ) {
            items(moviesState.value.size) { index ->
                val movie = moviesState.value[index]

                // Concaténation des chemins d'images
                val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

                // Utilisation d'une colonne pour chaque film
                Column(
                    modifier = Modifier.padding(16.dp), // Padding autour de chaque film
                    horizontalAlignment = Alignment.CenterHorizontally // Centrer les éléments
                ) {
                    // Image de l'affiche
                    AsyncImage(
                        model = posterUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f) // Ratio d'aspect pour l'affiche
                    )
                    // Titre du film
                    Text(
                        text = movie.title,
                        fontWeight = FontWeight.Bold, // Mettre le texte en gras
                        modifier = Modifier.padding(vertical = 4.dp) // Espacement autour du titre
                    )
                    // Date de sortie du film
                    Text(
                        text = movie.release_date,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(vertical = 4.dp) // Espacement autour de la date
                    )
                }
            }
        }
}
}
