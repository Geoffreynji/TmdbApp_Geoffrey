import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import coil.compose.rememberImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.premiereapplication.ui.theme.FilmsDetails
import com.example.premiereapplication.ui.theme.MainViewModel
import com.example.premiereapplication.ui.theme.SeriesDetails
import com.example.premiereapplication.ui.theme.SeriesDetailsScreen
import com.example.premiereapplication.ui.theme.TmdbSeries

@Composable
fun Series(viewModel: MainViewModel, navController: NavHostController) {
    // Charger les séries au démarrage
    LaunchedEffect(Unit) {
        viewModel.getSeries()
    }

    // Récupérer la liste des séries
    val series by viewModel.series.collectAsState()

    // Liste des séries avec LazyColumn
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(series) { serie ->
            SeriesItem(serie, navController)
        }
    }
}

// Composant pour afficher une série avec son image et son titre
@Composable
fun SeriesItem(serie: TmdbSeries, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(SeriesDetails(serie.id))
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF435b1e) // Fond noir légèrement transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp // Élévation pour un léger effet d'ombre
        )
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            // Image de la série
            val imageUrl = "https://image.tmdb.org/t/p/w500${serie.poster_path}"
            val painter: Painter = rememberAsyncImagePainter(imageUrl)

            Image(
                painter = painter,
                contentDescription = serie.name,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(0.67f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Titre de la série et d'autres informations (si disponibles)
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            ) {
                Text(
                    text = serie.name,
                    fontFamily = FontFamily.SansSerif, // Utilisation de la police intégrée SansSerif
                    fontWeight = FontWeight.Bold, // Met le texte en gras
                    fontStyle = FontStyle.Italic, // Applique l'italique
                    fontSize = 19.sp, // Taille de police plus grande
                    color = Color.White // Texte en blanc pour contraster avec le fond
                )
                Spacer(modifier = Modifier.height(4.dp))

            }
        }
    }
}
