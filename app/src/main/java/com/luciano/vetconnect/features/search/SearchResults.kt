package com.luciano.vetconnect.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.data.models.Veterinary
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.shared.ui.components.VetCard

@Composable
fun SearchResults(navController: NavController, onMenuClick: () -> Unit) {
    var veterinaries by remember { mutableStateOf<List<Veterinary>>(emptyList()) }

    LaunchedEffect(Unit) {
        veterinaries = List(4) {
            Veterinary(
                name = "Clinica Veterinaria - El Roble",
                address = "Jr Callao Nro 894, Callao",
                rating = 4,
                clinicPrice = "S/. 60",
                bathPrice = "S/. 30"
            )
        }
    }

    Scaffold(
        topBar = { com.luciano.vetconnect.navigation.TopAppBar(onMenuClick = onMenuClick) },
        containerColor = SecondaryOrange2
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                FilterButtons()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Encontramos ${veterinaries.size} resultados para \"El Roble\"",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkGreen,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(veterinaries) { veterinary ->
                VetCard(
                    veterinary = veterinary,
                    onVetClick = {
                        navController.navigate(Screen.VetDetail.route)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FilterButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = { /* TODO: Implement filter */ },
            modifier = Modifier
                .weight(1f)
                .border(2.dp, PrimaryGreen, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Filtrar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            onClick = { /* TODO: Implement sort */ },
            modifier = Modifier
                .weight(1f)
                .border(2.dp, Gray1, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Ordenar por",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Gray1
            )
        }
    }
}