package com.example.premiereapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.premiereapplication.R


@Composable
fun Screen(windowClass: WindowSizeClass, navController: NavController) {
    when (windowClass.windowWidthSizeClass)
    {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "Un chat",
                    modifier = Modifier
                        .size(300.dp) // Ajuste la taille à tes besoins
                        .aspectRatio(1f) // Force le ratio 1:1 pour obtenir un carré
                        .clip(CircleShape) // Découpe l'image en cercle
                )
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Geoffrey Naji",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Etudiant ISIS FIE4",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp)
                )
                Row()
                {
                    Icon(
                        painterResource(id = R.drawable.mail),
                        contentDescription = "Un chat",
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "geoffrey.naji@gmail.com",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Row()
                {
                    Icon(
                        painterResource(id = R.drawable.baseline_link_24),
                        contentDescription = "linkdedin",
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "www.linkedin.com/in/geoffrey-naji",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                FilledButton(onClick = {
                    navController.navigate(FilmsDestination())
                })
            }
        }
        else -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Column()
                {
                    Image(
                        painterResource(id = R.drawable.chat),
                        contentDescription = "Un chat",
                        modifier = Modifier
                            .size(300.dp) // Ajuste la taille à tes besoins
                            .aspectRatio(1f) // Force le ratio 1:1 pour obtenir un carré
                            .clip(CircleShape) // Découpe l'image en cercle
                    )

                    Text(
                        text = "Geoffrey Naji",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(2.dp)
                    )
                    Text(
                        text = "Etudiant ISIS FIE4",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Column( horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,)
                {
                    Row()
                    {
                        Icon(
                            painterResource(id = R.drawable.mail),
                            contentDescription = "Un chat",
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "geoffrey.naji@gmail.com",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Row()
                    {
                        Icon(
                            painterResource(id = R.drawable.baseline_link_24),
                            contentDescription = "Un chat",
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "www.linkedin.com/in/geoffrey-naji",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    FilledButton(onClick = {
                        navController.navigate(FilmsDestination())
                    })

                }
            }
        }
    }
}

@Composable
fun FilledButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF415622), // Couleur de fond du bouton
            contentColor = Color.White   // Couleur du texte
        )
    ) {
        Text("Démarrer")
    }
}
