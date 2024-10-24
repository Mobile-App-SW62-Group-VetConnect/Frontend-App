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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.navigation.TopAppBar

@Composable
fun VetDetailScreen(navController: NavController, onMenuClick: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(onMenuClick = onMenuClick) },
        containerColor = BackgroundColors.Primary
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                VetImage()
                VetInfo()
                ActionButtons()
                ServicesSection()
                AddressSection()
                ReviewsSection()
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
                        .background(if (index == 0) BrandColors.Primary else TextColors.OnDark)
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
            color = TextColors.Primary
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(4) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_filled),
                    contentDescription = "Star",
                    tint = BrandColors.Secondary,
                    modifier = Modifier.size(16.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_star_outline),
                contentDescription = "Star",
                tint = BrandColors.Secondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "233 Reviews",
                color = TextColors.Secondary,
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
                .background(BrandColors.Primary)
        ) {
            when (icon) {
                is ImageVector ->
                    Icon(imageVector = icon, contentDescription = text, tint = TextColors.OnDark)
                is Painter ->
                    Icon(painter = icon, contentDescription = text, tint = TextColors.OnDark)
            }
        }
        Text(text = text, fontSize = 12.sp, color = TextColors.Primary)
    }
}

@Composable
fun ServicesSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundColors.Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Servicios",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextColors.Primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            ServiceItem("Consulta clínica", "Price S/. 60")
            ServiceItem("Baño", "Price S/. 30")
        }
    }
}

@Composable
fun ServiceItem(name: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, color = TextColors.Primary)
        Text(text = price, color = TextColors.Secondary)
    }
}

@Composable
fun AddressSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundColors.Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Dirección",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextColors.Primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Jr Callao Nro 894, Callao",
                color = TextColors.Primary
            )
        }
    }
}

@Composable
fun ReviewsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundColors.Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Reseñas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextColors.Primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            ReviewItem()
            ReviewItem()
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Implement review input */ },
                placeholder = { Text("Escribe una reseña...", color = TextColors.Secondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandColors.Primary,
                    unfocusedBorderColor = NeutralColors.Gray2
                ),
                trailingIcon = {
                    IconButton(onClick = { /* TODO: Submit review */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Submit Review",
                            tint = BrandColors.Primary
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ReviewItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_avatar),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Nombre de usuario",
                fontWeight = FontWeight.Bold,
                color = TextColors.Primary
            )
            Text(
                text = "Hace x días",
                color = TextColors.Secondary,
                fontSize = 12.sp
            )
            Row {
                repeat(4) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star_filled),
                        contentDescription = "Star",
                        tint = BrandColors.Secondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_outline),
                    contentDescription = "Star",
                    tint = BrandColors.Secondary,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = "Texto de la reseña",
                fontSize = 14.sp,
                color = TextColors.Primary
            )
        }
    }
}