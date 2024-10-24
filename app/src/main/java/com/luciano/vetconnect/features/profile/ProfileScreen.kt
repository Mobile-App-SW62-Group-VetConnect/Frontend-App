package com.luciano.vetconnect.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", color = TextColors.Primary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = BrandColors.Primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.EditProfile.route) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar perfil",
                            tint = BrandColors.Primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColors.Primary
                )
            )
        },
        containerColor = BackgroundColors.Primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Sección de perfil
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Carlos Chávez",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextColors.Primary
                )

                Text(
                    text = "Usuario desde Octubre 2024",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColors.Secondary
                )
            }

            Divider(color = NeutralColors.Gray1)

            // Información personal
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextColors.Primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ProfileInfoItem(
                    icon = Icons.Outlined.Email,
                    label = "Correo electrónico",
                    value = "carlos.chavez@example.com"
                )

                ProfileInfoItem(
                    icon = Icons.Outlined.Phone,
                    label = "Teléfono",
                    value = "987654321"
                )

                ProfileInfoItem(
                    icon = Icons.Outlined.LocationOn,
                    label = "Dirección",
                    value = "Jr. Las Palmeras 123"
                )
            }

            Divider(color = NeutralColors.Gray1)

            // Estadísticas
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Actividad",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextColors.Primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatisticItem(
                        number = "12",
                        label = "Reseñas"
                    )
                    StatisticItem(
                        number = "8",
                        label = "Veterinarias\nGuardadas"
                    )
                    StatisticItem(
                        number = "15",
                        label = "Citas\nRegistradas"
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrandColors.Primary,
            modifier = Modifier.size(24.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = TextColors.Primary
            )
        }
    }
}

@Composable
private fun StatisticItem(
    number: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineMedium,
            color = BrandColors.Primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextColors.Secondary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}