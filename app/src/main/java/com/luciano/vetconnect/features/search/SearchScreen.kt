package com.luciano.vetconnect.features.search

import com.luciano.vetconnect.shared.ui.components.SearchBarItem
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luciano.vetconnect.shared.ui.theme.PrimaryGreen
import com.luciano.vetconnect.shared.ui.theme.SecondaryOrange2


@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SecondaryOrange2
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SearchBarItem {
                newText -> searchText = newText
                navController.navigate("searchResultScreen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Búsquedas Recientes",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchChip(text = "El Roble")
                SearchChip(text = "Baño y Corte de pelo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Servicios más buscados",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchChip(text = "Corte de uñas")
                SearchChip(text = "Baño y Corte de pelo")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchChip(text = "Spa para gatos")
                SearchChip(text = "Spa para perros")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Otros también buscaron
            Text(
                text = "Otros también buscaron",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchChip(text = "Ricocan")
                SearchChip(text = "Medicamentos para perros")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchChip(text = "Guardería")
            }
        }
    }
}

@Composable
fun SearchChip(text: String) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen,
        ),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .wrapContentWidth()
            .defaultMinSize(minHeight = 32.dp)
            .padding(4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}
