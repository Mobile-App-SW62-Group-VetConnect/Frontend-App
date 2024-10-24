package com.luciano.vetconnect.shared.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.shared.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones", color = TextColors.Primary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
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
        ) {
            // Descripción general
            Text(
                text = "Personaliza tus notificaciones",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = TextColors.Primary,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
            )

            Text(
                text = "Selecciona los tipos de notificaciones que deseas recibir",
                color = TextColors.Secondary,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp)
            )

            // Sección de Actualizaciones
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                NotificationSettingItem(
                    NotificationOption(
                        icon = Icons.Outlined.LocalOffer,
                        title = "Promociones",
                        description = "Recibe notificaciones sobre ofertas y descuentos"
                    )
                )

                NotificationSettingItem(
                    NotificationOption(
                        icon = Icons.Outlined.Star,
                        title = "Reseñas",
                        description = "Recibe notificaciones sobre respuestas a tus reseñas"
                    )
                )

                NotificationSettingItem(
                    NotificationOption(
                        icon = Icons.Outlined.Notifications,
                        title = "Noticias",
                        description = "Recibe noticias y actualizaciones importantes"
                    )
                )
            }
        }
    }
}

private data class NotificationOption(
    val icon: ImageVector,
    val title: String,
    val description: String
)

@Composable
private fun NotificationSettingItem(
    option: NotificationOption
) {
    var isEnabled by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = option.icon,
            contentDescription = null,
            tint = BrandColors.Primary,
            modifier = Modifier.size(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = option.title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextColors.Primary
            )
            Text(
                text = option.description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextColors.Secondary
            )
        }

        Switch(
            checked = isEnabled,
            onCheckedChange = { isEnabled = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = TextColors.OnDark,
                checkedTrackColor = BrandColors.Primary,
                uncheckedThumbColor = NeutralColors.Gray2,
                uncheckedTrackColor = NeutralColors.Gray1
            )
        )
    }
}