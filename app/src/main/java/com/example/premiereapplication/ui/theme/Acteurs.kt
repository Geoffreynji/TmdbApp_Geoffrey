package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

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
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 100.dp, bottom = 90.dp) //Ajoute un padding en haut et bas pour que tous les items soient dans le cadre
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

        //Affichage de l'image de l'acteur
        Image(
            painter = painter,
            contentDescription = actor.name,
            modifier = Modifier
                .size(120.dp) // Taille de l'image
                .clip(RoundedCornerShape(10.dp)), // Coins arrondis pour l'image
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacement entre l'image et le texte en hauteur

        // Nom de l'acteur
        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodyLarge, // Style de texte
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center // Centrer le texte
        )
    }
}