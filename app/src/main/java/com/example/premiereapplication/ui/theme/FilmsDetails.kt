import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.premiereapplication.ui.theme.MainViewModel
import kotlin.math.floor

@Composable
fun FilmDetailsScreen(viewModel: MainViewModel, filmId: Int) {
    // Charger les détails du film au démarrage
    LaunchedEffect(filmId) {
        viewModel.getMovieDetails(filmId)
        viewModel.getMovieCast(filmId)
    }

    // Récupérer les détails du film
    val movieDetails by viewModel.movieDetails.collectAsState()
    // Récupérer acteurs qui ont joué dans le film
    val movieCast by viewModel.movieCast.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        movieDetails?.let { movie ->
            item {
                val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

                Spacer(modifier = Modifier.height(80.dp))

                AsyncImage(
                    model = posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Date de sortie: ${movie.release_date}",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Durée: ${movie.runtime} min", fontWeight = FontWeight.Bold)
                }

                val genreNames = movie.genres.joinToString(", ") { it.name }
                Text(
                    text = "Genres: $genreNames",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                val spokenLanguages = movie.spoken_languages.joinToString(", ") { it.english_name }
                Text(
                    text = "Langue(s): $spokenLanguages",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                val voteAverage = movie.vote_average
                val ratingOutOfFive = (voteAverage / 2)
                val fullStars = floor(ratingOutOfFive).toInt()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Note: $voteAverage (${movie.vote_count} votes)",
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    repeat(fullStars) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Étoile pleine",
                            tint = Color(0xFFFFA500)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Affichage des acteurs
                Text(
                    text = "Acteurs principaux",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Limiter l'affichage aux 6 premiers acteurs et les diviser en paires de 2
                movieCast.take(6).chunked(2).forEach { actorPair ->
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

