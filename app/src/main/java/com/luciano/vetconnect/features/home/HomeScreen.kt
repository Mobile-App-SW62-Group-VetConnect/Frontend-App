package com.luciano.vetconnect.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.navigation.TopAppBar
import com.luciano.vetconnect.shared.ui.theme.*

data class VetClinic(
    val name: String,
    val rating: Float,
    val reviews: Int,
    val address: String,
    val consultPrice: String,
    val bathPrice: String,
    val imageRes: Int
)

@Composable
fun HomeScreen(navController: NavController, onMenuClick: () -> Unit) {
    val vetClinics = List(4) {
        VetClinic(
            name = "Clinica Veterinaria - El Roble",
            rating = 4f,
            reviews = 233,
            address = "Jr Callao Nro 894, Callao",
            consultPrice = "S/. 60",
            bathPrice = "S/. 30",
            imageRes = R.drawable.vet_clinic
        )
    }

    Scaffold(
        topBar = { TopAppBar(onMenuClick = onMenuClick) },
        containerColor = SecondaryOrange2
    ) { paddingValues ->
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
                    color = TextDarkGreen,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            items(vetClinics) { clinic ->
                VetClinicCard(
                    clinic = clinic,
                    onClinicClick = {
                        navController.navigate(Screen.VetDetail.route)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun VetClinicCard(clinic: VetClinic, onClinicClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClinicClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = clinic.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkGreen
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < clinic.rating.toInt()) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                        ),
                        contentDescription = null,
                        tint = SecondaryOrange,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "${clinic.reviews} Reviews",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Image(
                painter = painterResource(id = clinic.imageRes),
                contentDescription = "Clinic Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Dirección: ${clinic.address}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Consulta clínica",
                    fontSize = 14.sp,
                    color = TextDarkGreen
                )
                Text(
                    text = "Price ${clinic.consultPrice}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkGreen
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Baño",
                    fontSize = 14.sp,
                    color = TextDarkGreen
                )
                Text(
                    text = "Price ${clinic.bathPrice}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkGreen
                )
            }
        }
    }
}