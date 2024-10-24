package com.luciano.vetconnect.shared.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        colors = CardDefaults.cardColors(containerColor = BackgroundColors.Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = veterinary.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextColors.Primary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < veterinary.rating) R.drawable.ic_star_filled
                            else R.drawable.ic_star_outline
                        ),
                        contentDescription = "Star",
                        tint = BrandColors.Secondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "${veterinary.rating}/5",
                    fontSize = 14.sp,
                    color = TextColors.Secondary,
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
                color = TextColors.Secondary,
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
                        color = TextColors.Primary
                    )
                    Text(
                        text = veterinary.clinicPrice,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextColors.Primary
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
                        color = TextColors.Primary
                    )
                    Text(
                        text = veterinary.bathPrice,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextColors.Primary
                    )
                }
            }
        }
    }
}