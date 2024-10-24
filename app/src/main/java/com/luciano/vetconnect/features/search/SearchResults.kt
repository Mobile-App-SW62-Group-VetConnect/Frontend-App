package com.luciano.vetconnect.features.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.shared.ui.components.VetCard
import com.luciano.vetconnect.shared.ui.components.FilterButtons
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.data.models.Veterinary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResults(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
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
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = BrandColors.Secondary
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            shape = MaterialTheme.shapes.medium,
                            color = BackgroundColors.Primary
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = null,
                                    tint = TextColors.Secondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                BasicTextField(
                                    value = searchText,
                                    onValueChange = { searchText = it },
                                    modifier = Modifier.weight(1f),
                                    decorationBox = { innerTextField ->
                                        Box {
                                            if (searchText.isEmpty()) {
                                                Text(
                                                    "¿Qué estás buscando?",
                                                    color = TextColors.Secondary,
                                                    fontSize = 16.sp
                                                )
                                            }
                                            innerTextField()
                                        }
                                    },
                                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                                        color = TextColors.Primary,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BackgroundColors.Secondary
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        containerColor = BackgroundColors.Primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FilterButtons()

            Text(
                text = "Encontramos ${veterinaries.size} resultados",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = TextColors.Primary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(veterinaries) { veterinary ->
                    VetCard(
                        veterinary = veterinary,
                        onVetClick = {
                            navController.navigate(Screen.VetDetail.route)
                        }
                    )
                }
            }
        }
    }
}