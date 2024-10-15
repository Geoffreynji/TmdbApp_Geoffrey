import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter // Assurez-vous d'ajouter la dépendance Coil dans votre build.gradle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import coil.compose.rememberAsyncImagePainter
import com.example.premiereapplication.ui.theme.MainViewModel
import com.example.premiereapplication.ui.theme.TmdbSeries

@Composable
fun Series(viewModel: MainViewModel) {
    // Charger les séries au démarrage
    LaunchedEffect(Unit) {
        viewModel.getSeries()  // Appel pour récupérer les séries via l'API
    }

    // Récupérer la liste des séries
    val series by viewModel.series.collectAsState()

    // Liste des séries avec LazyColumn
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(series) { serie ->
            SeriesItem(serie)
        }
    }
}

// Composant pour afficher une série avec son image et son titre
@Composable
fun SeriesItem(serie: TmdbSeries) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            // Image de la série
            val imageUrl = "https://image.tmdb.org/t/p/w500${serie.poster_path}" // URL de l'image
            val painter: Painter = rememberAsyncImagePainter(imageUrl)

            Image(
                painter = painter,
                contentDescription = serie.name,
                modifier = Modifier
                    .size(100.dp) // Taille de l'image
                    .aspectRatio(0.67f), // Aspect ratio
                contentScale = ContentScale.Crop // Recadrage de l'image
            )

            Spacer(modifier = Modifier.width(10.dp)) // Espacement entre l'image et le texte

            // Titre de la série
            Column(
                modifier = Modifier.align(Alignment.CenterVertically) // Alignement vertical
            ) {
                Text(
                    text = serie.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
