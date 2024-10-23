package com.luciano.vetconnect.features.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.ui.components.SearchBarItem
import com.luciano.vetconnect.shared.ui.theme.*
import androidx.compose.ui.text.font.FontWeight

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
                navController.navigate(Screen.SearchResults.route)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Búsquedas Recientes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkGreen
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ChipsRow(
                items = listOf("El Roble", "Baño y Corte de pelo"),
                onChipClick = { navController.navigate(Screen.SearchResults.route) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Servicios más buscados",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkGreen
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ChipsRow(
                items = listOf("Corte de uñas", "Baño y Corte de pelo"),
                onChipClick = { navController.navigate(Screen.SearchResults.route) }
            )

            ChipsRow(
                items = listOf("Spa para gatos", "Spa para perros"),
                onChipClick = { navController.navigate(Screen.SearchResults.route) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Otros también buscaron",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkGreen
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ChipsRow(
                items = listOf("Ricocan", "Medicamentos para perros"),
                onChipClick = { navController.navigate(Screen.SearchResults.route) }
            )

            ChipsRow(
                items = listOf("Guardería"),
                onChipClick = { navController.navigate(Screen.SearchResults.route) }
            )
        }
    }
}

@Composable
fun ChipsRow(
    items: List<String>,
    onChipClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        items.forEach { text ->
            SuggestionChip(
                onClick = onChipClick,
                label = { Text(text = text) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = PrimaryGreen,
                    labelColor = PrimaryWhite
                )
            )
        }
    }
}