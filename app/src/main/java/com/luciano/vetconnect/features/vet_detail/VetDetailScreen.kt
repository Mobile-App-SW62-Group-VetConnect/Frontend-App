package com.luciano.vetconnect.features.vet_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luciano.vetconnect.R
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.navigation.TopAppBar

@Composable
fun VetDetailScreen() {
    Scaffold(
        topBar = { TopAppBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SecondaryOrange2)
        ) {
            item {
                VetImage()
                VetInfo()
                ActionButtons()

            }
        }
    }
}

@Composable
fun VetImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.vet_clinic),
            contentDescription = "Veterinary Clinic",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            repeat(5) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (index == 0) PrimaryGreen else Color.White)
                        .padding(end = 4.dp)
                )
            }
        }
    }
}

@Composable
fun VetInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Clinica Veterinaria - El Roble",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = PrimaryGreen
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(4) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_filled),
                    contentDescription = "Star",
                    tint = SecondaryOrange,
                    modifier = Modifier.size(16.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_star_outline),
                contentDescription = "Star",
                tint = SecondaryOrange,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "233 Reviews",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(icon = painterResource(id = R.drawable.ic_bookmark), text = "Guardar")
        ActionButton(icon = Icons.Default.Share, text = "Compartir")
        ActionButton(icon = Icons.Default.ArrowForward, text = "Comparar")
    }
}

@Composable
fun ActionButton(icon: Any, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { /* TODO: Implement action */ },
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(PrimaryGreen)
        ) {
            when (icon) {
                is ImageVector ->
                    Icon(imageVector = icon, contentDescription = text, tint = Color.White)
                is Painter ->
                    Icon(painter = icon, contentDescription = text, tint = Color.White)
            }
        }
        Text(text = text, fontSize = 12.sp, color = PrimaryGreen)
    }
}
