package com.luciano.vetconnect.shared.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.luciano.vetconnect.R
import com.luciano.vetconnect.shared.data.models.Veterinary
import com.luciano.vetconnect.shared.ui.theme.*

@Composable
fun VetCard(
    veterinary: Veterinary,
    onVetClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onVetClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = veterinary.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkGreen
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                val maxStars = 5
                (0 until maxStars).forEach { index ->
                    val isFilled = index < veterinary.rating
                    val iconId = if (isFilled) {
                        R.drawable.ic_star_filled
                    } else {
                        R.drawable.ic_star_outline
                    }

                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = if (isFilled) "Estrella llena" else "Estrella vacía",
                        tint = SecondaryOrange,
                        modifier = Modifier.size(16.dp)
                    )

                    if (index < maxStars - 1) {
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }

                Text(
                    text = "${veterinary.rating}/5",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.vet_clinic),
                contentDescription = "Imagen de la veterinaria",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Dirección: ${veterinary.address}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Consulta clínica",
                        fontSize = 14.sp,
                        color = TextDarkGreen
                    )
                    Text(
                        text = veterinary.clinicPrice,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkGreen
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Baño",
                        fontSize = 14.sp,
                        color = TextDarkGreen
                    )
                    Text(
                        text = veterinary.bathPrice,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkGreen
                    )
                }
            }
        }
    }
}