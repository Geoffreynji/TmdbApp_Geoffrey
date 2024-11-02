package com.example.premiereapplication.ui.theme

import FilmDetailsScreen
import Series
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable class ProfilDestination
@Serializable class FilmsDestination
@Serializable class SeriesDestination
@Serializable class ActeursDestination
@Serializable class FilmsDetails(val id : Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            //val currentDestination = navBackStackEntry?.ProfilDestination
            Screen(windowSizeClass, navController)
            PremiereApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        //Mettre condition ici pour ne pas afficher barre navigation sur page d'acceuil
                        // Afficher la TopBar sauf si on est sur l'écran "Profil"
                            MyTopBar(viewModel())
                    },
                    bottomBar = {
                        //Mettre condition ici pour ne pas afficher barre navigation sur page d'acceuil
                            MyBottomBar(navController)
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding) // Applique le padding
                    )
                }
            }
        }
    }


    @Composable
    fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val viewModel: MainViewModel = viewModel()
        NavHost(navController = navController, startDestination = ProfilDestination()) {
            composable<FilmsDestination> { Films(viewModel, navController) }
            composable<ProfilDestination> { Screen(windowSizeClass, navController) }
            composable<SeriesDestination> { Series(viewModel) }
            composable<ActeursDestination> {Actors(viewModel)}
            composable<FilmsDetails> { backStackEntry ->
            val filmDetail: FilmsDetails = backStackEntry.toRoute()
            FilmDetailsScreen(viewModel, filmDetail.id)
        }
            /* ajouter ici composable acteurs */
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyTopBar(viewModel: MainViewModel) {
        var isSearchMode by remember { mutableStateOf(false) }  // Gestion de l'état de la barre (normale ou recherche)
        var searchText by remember { mutableStateOf(TextFieldValue("")) } // Gestion de l'état du texte dans la barre de recherche

        // Affiche la barre normale ou la barre de recherche selon l'état
        if (isSearchMode) {
            // Barre de recherche
            TopAppBar(
                title = {
                    TextField(
                        value = searchText.text,
                        onValueChange = { query ->
                            searchText = TextFieldValue(query)
                            viewModel.searchFilms(query)  // Appel de la recherche dès que l'utilisateur tape
                        },
                        placeholder = { Text("Rechercher un film...") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF4CAF50),
                            unfocusedContainerColor = Color(0xFF81C784),
                            focusedTextColor = Color(0xFF1B5E20),
                            unfocusedTextColor = Color(0xFF388E3C)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                    // Icône de fermeture pour quitter le mode recherche
                    IconButton(onClick = {
                        isSearchMode = false  // Quitter le mode recherche
                        searchText = TextFieldValue("")  // Réinitialiser le texte de recherche
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Fermer la recherche", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50)  // Ajuste selon ton design
                )
            )
        } else {
            // Barre normale avec icône de recherche
            TopAppBar(
                title = { Text("GeoffreyMovies") },  // Titre de l'application
                actions = {
                    // Icône de loupe pour lancer la recherche
                    IconButton(onClick = { isSearchMode = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Rechercher", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50)  // Couleur de la barre normale
                )
            )
        }
    }


    @Composable
    fun MyBottomBar(navController: NavHostController) {
        BottomAppBar(
            content = {
                Row(
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Espace les boutons uniformément
                ) {
                    TextButton(onClick = { navController.navigate(FilmsDestination()) }) {
                        Text(text = "Films", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate(SeriesDestination()) }) {
                        Text(text = "Séries", color = Color.White)
                    }
                    TextButton(onClick = { navController.navigate(ActeursDestination()) }) {
                        Text(text = "Acteurs", color = Color.White)
                    }
                }
            },
            containerColor = Color(0xFF81C784),
        )
    }
}

