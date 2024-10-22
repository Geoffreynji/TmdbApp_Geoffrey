package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun Actors(viewModel: MainViewModel) {
    // Charger les acteurs au démarrage
    LaunchedEffect(Unit) {
        viewModel.getActors()  // Appel pour récupérer les acteurs via l'API
    }

    // Récupérer la liste des acteurs
    val actors by viewModel.actors.collectAsState()

    // Grille des acteurs avec LazyVerticalGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 colonnes
        modifier = Modifier.padding(10.dp)
    ) {
        items(actors) { actor ->
            ActorItem(actor) // Appel à un composant personnalisé pour afficher chaque acteur
        }
    }
}

// Composant pour afficher un acteur avec son image et son nom
@Composable
fun ActorItem(actor: TmdbActor) {
    // Mise en page pour l'acteur
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Alignement centré
    ) {
        // Image de l'acteur
        val imageUrl = "https://image.tmdb.org/t/p/w500${actor.profile_path}"
        val painter: Painter = rememberAsyncImagePainter(imageUrl)

        Image(
            painter = painter,
            contentDescription = actor.name,
            modifier = Modifier
                .size(120.dp) // Taille de l'image
                .clip(RoundedCornerShape(10.dp)), // Coins arrondis pour l'image
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre l'image et le texte

        // Nom de l'acteur
        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodyLarge, // Style de texte
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center // Centrer le texte
        )
    }
}