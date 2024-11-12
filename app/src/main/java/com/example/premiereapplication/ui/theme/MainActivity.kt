package com.example.premiereapplication.ui.theme

import FilmDetailsScreen
import Series
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.NavHost
import androidx.navigation.NavDestination.Companion.hasRoute

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.serialization.Serializable

@Serializable class ProfilDestination
@Serializable class FilmsDestination
@Serializable class SeriesDestination
@Serializable class ActeursDestination
@Serializable class FilmsDetails(val id : Int)
@Serializable class SeriesDetails(val id : Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            // Vérifie si la destination actuelle n'est pas "ProfilDestination"
            val showBars = navBackStackEntry?.destination?.hasRoute<ProfilDestination>() ?: true
            Screen(windowSizeClass, navController)
            PremiereApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        // Afficher la TopBar seulement si l'écran est en portrait
                        if (!showBars) {
                            when (windowSizeClass.windowWidthSizeClass) {
                                WindowWidthSizeClass.COMPACT -> {
                                    MyTopBar(viewModel())
                                }
                            }
                        }
                    },
                    bottomBar = {
                        // Afficher la bottombar en fonction de l'orientation de l'écran
                        if (!showBars) {
                            when (windowSizeClass.windowWidthSizeClass) {
                                WindowWidthSizeClass.COMPACT -> {
                                    // Afficher la BottomAppBar en mode portrait
                                    MyBottomBar(navController)
                                }

                                WindowWidthSizeClass.MEDIUM, WindowWidthSizeClass.EXPANDED -> {
                                    // Ne pas afficher la BottomAppBar, on gère cela dans le contenu avec innerpadding
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    // Disposition en fonction de la taille de l'écran
                    when (windowSizeClass.windowWidthSizeClass) {
                        WindowWidthSizeClass.COMPACT -> {
                            // Contenu normal
                            AppNavigation(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding) // Applique le padding
                            )
                        }

                        WindowWidthSizeClass.MEDIUM, WindowWidthSizeClass.EXPANDED -> {
                            // En mode paysage, afficher AppNavigation à côté de PaysageBottomBar
                            Row(
                                modifier = Modifier
                                    .padding(innerPadding) // Applique le padding
                                    .fillMaxSize() // Remplit tout l'espace
                            ) {
                                // Afficher PaysageBottomBar
                                PaysageBottomBar(navController)

                                // Afficher AppNavigation
                                AppNavigation(
                                    navController = navController,
                                    modifier = Modifier
                                        .padding(innerPadding) // Applique le padding
                                        .weight(1f) // Permet à AppNavigation de prendre le reste de l'espace
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    //Ici on gère la navigation avec les différents composable
    @Composable
    fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val viewModel: MainViewModel = viewModel()
        NavHost(navController = navController, startDestination = ProfilDestination()) {
            composable<FilmsDestination> { Films(viewModel, navController) }
            composable<ProfilDestination> { Screen(windowSizeClass, navController) }
            composable<SeriesDestination> { Series(viewModel, navController) }
            composable<ActeursDestination> { Actors(viewModel) }
            composable<FilmsDetails> { backStackEntry ->
                val filmDetail: FilmsDetails = backStackEntry.toRoute()
                FilmDetailsScreen(viewModel, filmDetail.id)
            }
            composable<SeriesDetails> { backStackEntry ->
                val serieDetail: SeriesDetails = backStackEntry.toRoute()
                SeriesDetailsScreen(viewModel, serieDetail.id)
            }
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
                            viewModel.searchSeries(query)
                            viewModel.searchActors(query)

                        },
                        placeholder = { Text("Rechercher ...") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFdce9ca),
                            unfocusedContainerColor = Color(0xFF749943),
                            focusedTextColor = Color(0xFF000000),
                            unfocusedTextColor = Color(0xFF000000)
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
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Fermer la recherche",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF415622)  // Ajuste selon ton design
                )
            )
        } else {
            // Barre normale avec icône de recherche
            TopAppBar(
                title = {
                    Text(
                        "MoviesFlix",
                        color = Color.White,
                        fontStyle = FontStyle.Italic
                    )  // Titre de l'application avec texte en blanc
                },
                actions = {
                    // Icône de loupe pour lancer la recherche
                    IconButton(onClick = { isSearchMode = true }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Rechercher",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF415622)  // Couleur de la barre normale
                )
            )
        }
    }


    @Composable
    fun MyBottomBar(navController: NavHostController, ) {
        // Affichage habituel en mode portrait
        BottomAppBar(
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
            containerColor = Color(0xFF415622),
        )
    }

    //Composant correspondant à la bottom bar qui s'affichera en mode paysage
    @Composable
    fun PaysageBottomBar(navController: NavHostController) {
        // Barre de navigation en mode paysage
        Column(
            modifier = Modifier
                .fillMaxHeight() // Limite la hauteur de la barre
                .width(80.dp) // Largeur de la barre
                .background(Color(0xFF415622)), // Couleur de fond de la barre
            verticalArrangement = Arrangement.SpaceEvenly, // Espace les boutons uniformément
            horizontalAlignment = Alignment.Start // Alignement à gauche
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
    }
}
