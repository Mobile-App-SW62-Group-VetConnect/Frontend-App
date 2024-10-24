package com.luciano.vetconnect.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.data.models.Veterinary
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.shared.ui.components.VetCard
import com.luciano.vetconnect.shared.utils.fetchVeterinarias
import com.luciano.vetconnect.shared.utils.isNetworkAvailable

@Composable
fun HomeScreen(navController: NavController, onMenuClick: () -> Unit) {
    val context = LocalContext.current
    var veterinaries by remember { mutableStateOf<List<Veterinary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            if (isNetworkAvailable(context)) {
                veterinaries = fetchVeterinarias()
            } else {
                error = "No hay conexión a internet"
            }
        } catch (e: Exception) {
            error = "Error al cargar las veterinarias: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { com.luciano.vetconnect.navigation.TopAppBar(onMenuClick = onMenuClick) },
        containerColor = BackgroundColors.Primary
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BrandColors.Primary)
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error!!,
                        color = SemanticColors.Error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Text(
                            text = "Clínicas veterinarias cerca de ti",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextColors.Primary,
                            modifier = Modifier.padding(vertical = 16.dp)
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
    }
}
