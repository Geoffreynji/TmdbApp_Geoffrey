package com.example.premiereapplication.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val navController = rememberNavController()
            Screen(windowSizeClass, navController)
            PremiereApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding) // Applique le padding
                    )

                }
            }
        }
    }
}



@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    NavHost(navController = navController, startDestination = "Profil") {
        composable("Films") { Films() }
        composable("Profil") { Screen(windowSizeClass, navController) }
        composable("Series") {Series()}
    }
}
/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PremiereApplicationTheme {
        Greeting("Android")
    }
}*/
