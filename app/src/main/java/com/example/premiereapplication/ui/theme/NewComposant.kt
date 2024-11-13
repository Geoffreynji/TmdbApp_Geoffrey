package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun ComposantExam(viewModel: MainViewModel, navController: NavHostController) {
    // Récupérer l'état des collections
    val collections = viewModel.collections.collectAsState().value

    // Vérifie si la liste est vide et lance la recherche si nécessaire
    LaunchedEffect(collections) {
        if (collections.isEmpty()) {
            viewModel.searchCollections("horror")
        }
    }

    // Affichage des collections dans une LazyVerticalGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 colonnes dans la grille
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        items(collections.size) { index ->
            val collection = collections[index]

            // Affichage du nom de la collection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = collection.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp), // Espacement entre le texte et l'image
                    color = Color.Black
                )

                // Affichage de l'image de la collection si elle existe
                collection.poster_path?.let {
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${collection.poster_path}"),
                        contentDescription = "Poster de la collection",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Ajuste la taille de l'image
                            .padding(4.dp),
                        contentScale = ContentScale.Crop // Rognage de l'image pour remplir l'espace
                    )
                }
            }
        }
    }
}