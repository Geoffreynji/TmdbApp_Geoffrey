package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun SeriesDetailsScreen(viewModel: MainViewModel, serieId: Int) {
    LaunchedEffect(serieId) {
        viewModel.getSeriesDetails(serieId)
        viewModel.getSeriesCast(serieId)
    }

    //Collecter détails séries et acteurs
    val seriesDetails by viewModel.seriesDetails.collectAsState()
    val seriesActors by viewModel.seriesActors.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Ajoute du padding autour de la colonne
    ) {
        seriesDetails?.let { series ->
            item {
                // Affiche de la série
                val posterUrl = "https://image.tmdb.org/t/p/w500${series.poster_path}"

                //Pour gérer l'espace pris par la top barre
                Spacer(modifier = Modifier.height(80.dp))
                // Image de l'affiche
                AsyncImage(
                    model = posterUrl,
                    contentDescription = series.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                        .padding(bottom = 16.dp)
                )

                // Titre de la série
                Text(
                    text = series.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Synopsis
                Text(
                    text = series.overview,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Nombre de saisons et épisodes
                Text(
                    text = "Saisons : ${series.number_of_seasons}, Épisodes : ${series.number_of_episodes}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Genres
                val genreNames = series.genres.joinToString(", ") { it.name }
                Text(
                    text = "Genres : $genreNames",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Popularité et note moyenne
                Text(
                    text = "Popularité : ${series.popularity}",
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray
                )

                // Affichage de la note moyenne avec étoiles
                //Ici, j'ai voulu faire un système d'étoile qui prend en compte la note moyenne
                //Cela fonctionne mais j'aurai aussi pu rajouter les étoiles à moitié pleines (en image) quand le voteaverage n'est pas rond.
                //Ici uniquement des étoiles pleines s'affichent
                val voteAverage = series.vote_average
                val ratingOutOfFive = (voteAverage / 2).roundToInt() // Note sur 5
                val fullStars = floor(ratingOutOfFive.toDouble()).toInt()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Note moyenne : $voteAverage",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Affiche les étoiles
                    repeat(fullStars) {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Étoile pleine", tint = Color(0xFFFFA500))
                    }

                }

                // Langues parlées
                val spokenLanguages = series.spoken_languages.joinToString(", ") { it.name }
                Text(
                    text = "Langue(s) : $spokenLanguages",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Section des acteurs principaux
                Text(
                    text = "Acteurs principaux",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                //Limiter l'affichage aux 6 premiers acteurs et les diviser en paires de 2
                seriesActors.take(6).chunked(2).forEach { actorPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        actorPair.forEach { actor ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            ) {
                                val profileUrl =
                                    "https://image.tmdb.org/t/p/w200${actor.profile_path}"
                                //Image de l'acteur
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(profileUrl)
                                        .transformations(CircleCropTransformation()) // Transformation pour une image ronde parfaite
                                        .build(),
                                    contentDescription = actor.name,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(end = 8.dp)
                                )

                                Text(
                                    text = actor.name,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        // Ajouter un espace si la paire n'a qu'un acteur pour équilibrer
                        if (actorPair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
            }
        }
    }

