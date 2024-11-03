import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.premiereapplication.ui.theme.MainViewModel
import kotlin.math.floor

@Composable
fun FilmDetailsScreen(viewModel: MainViewModel, filmId: Int) {
    // Charger les détails du film au démarrage
    LaunchedEffect(filmId) {
        viewModel.getMovieDetails(filmId)
    }

    // Récupérer les détails du film
    val movieDetails by viewModel.movieDetails.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp) // Ajoute du padding autour de la colonne
    ) {
        movieDetails?.let { movie ->
            item {
                // Affiche de l'image du film
                val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

                //Pour gérer l'espace pris par la top barre
                Spacer(modifier = Modifier.height(80.dp))

                // Image de l'affiche du film
                AsyncImage(
                    model = posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f) // Ajuste le ratio d'aspect pour ressembler à une affiche de cinéma
                        .padding(bottom = 16.dp) // Padding en bas de l'image
                )

                // Titre du film
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Synopsis du film
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Informations supplémentaires
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Date de sortie
                    Text(text = "Date de sortie: ${movie.release_date}", fontWeight = FontWeight.Bold)

                    // Durée
                    Text(text = "Durée: ${movie.runtime} min", fontWeight = FontWeight.Bold)
                }

                // Genres
                val genreNames = movie.genres.joinToString(", ") { it.name }
                Text(
                    text = "Genres: $genreNames",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Langues parlées
                val spokenLanguages = movie.spoken_languages.joinToString(", ") { it.english_name }
                Text(
                    text = "Langue(s): $spokenLanguages",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Note moyenne avec étoiles
                val voteAverage = movie.vote_average
                val ratingOutOfFive = (voteAverage / 2)
                val fullStars = floor(ratingOutOfFive).toInt()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    // Texte de la note
                    Text(
                        text = "Note: $voteAverage (${movie.vote_count} votes)",
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Affiche les étoiles pleines en fonction de la note moyenne
                    repeat(fullStars) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Étoile pleine",
                            tint = Color(0xFFFFA500)
                        )
                    }
                }

                //Pour gérer l'espace pris par la bottom barre
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}



