package com.example.premiereapplication.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()
            Screen(windowSizeClass, navController)
            PremiereApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        //Mettre condition ici pour ne pas afficher barre navigation sur page d'acceuil
                            MyTopBar()
                    },
                    bottomBar = {
                        //Mettre condition ici pour ne pas afficher barre navigation sur page d'acceuil
                            MyBottomBar()
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
        NavHost(navController = navController, startDestination = "Profil") {
            composable("Films") { Films(viewModel) }
            composable("Profil") { Screen(windowSizeClass, navController) }
            composable("Series") { Series() }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyTopBar() {
        TopAppBar(
            title = { Text("My app") },
            colors = topAppBarColors(
                containerColor = Color(0xFF7921e2),
            ),
            actions = {

            }
        )
    }

    @Composable
    fun MyBottomBar() {
        BottomAppBar(
            content = {
                Row(
                    modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Espace les boutons uniformément
                ) {
                    TextButton(onClick = { /* Action pour Films */ }) {
                        Text(text = "Films", color = Color.White)
                    }
                    TextButton(onClick = { /* Action pour Séries */ }) {
                        Text(text = "Séries", color = Color.White)
                    }
                    TextButton(onClick = { /* Action pour Acteurs */ }) {
                        Text(text = "Acteurs", color = Color.White)
                    }
                }
            },
            containerColor = Color(0xFF7921e2),
        )
    }
}

