package com.luciano.vetconnect.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import com.luciano.vetconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    CenterAlignedTopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.logo), // Reemplaza con el nombre de tu ícono
            contentDescription = "Icono de la app",
            modifier = Modifier.size(180.dp)
        ) },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                    tint= Color(0xFFFFBB57),
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E4E3C)),
    )
}