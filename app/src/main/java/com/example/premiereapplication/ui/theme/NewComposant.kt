package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

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

    // Affichage de la liste des collections avec Column
    Column(modifier = Modifier.fillMaxWidth()) {
        // Passer la liste de collections à Column
        collections.forEach { collection ->
            // Afficher le nom de chaque collection
            Text(
                text = collection.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)  // Espacement pour rendre les éléments plus lisibles
            )
        }
    }
}




