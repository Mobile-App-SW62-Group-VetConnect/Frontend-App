package com.luciano.vetconnect.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.shared.ui.theme.SecondaryOrange2

@Composable
fun SearchResults(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SecondaryOrange2
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterButton(
                    text = "Filtrar",
                    icon = { },
                    borderColor = Color(0xFF4CAF50), // Borde verde
                    textColor = Color(0xFF000000),
                    onClick = { /* Acción para filtrar */ }
                )

                FilterButton(
                    text = "Ordenar por",
                    icon = { /* Aquí puedes colocar el icono de flecha abajo */ },
                    borderColor = Color(0xFF000000), // Borde negro
                    textColor = Color(0xFF000000),
                    onClick = { /* Acción para ordenar */ }
                )
            }
            Text(
                text = "Encontramos x resultados para “El Roble”",
                fontSize = 20.sp,
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            VetCenterCard()

        }
    }


}
@Preview()
@Composable
fun VetCenterCard(){
    Card(onClick = {},
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFF000000),
        ),
        modifier = Modifier.fillMaxWidth().height(300.dp),
        elevation =  CardDefaults.cardElevation(defaultElevation = 2.dp)


    ) {
        VetCenterCardContent()
    }
}
@Preview()
@Composable
fun VetCenterCardContent(){
    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "Clínica Veterinaria - El Roble", fontSize = 24.sp, lineHeight = 30.sp,
            maxLines = 2, overflow = TextOverflow.Ellipsis)
        Row {
            Image(
                painter = painterResource(id = R.drawable.vetcenter),
                contentDescription = "Centro Veterinario",
                modifier = Modifier.size(200.dp, 200.dp).padding(end = 10.dp),
            )
            Text(text = "Dirección: Jr Callao Nro 894, Callao")
        }

    }
}

@Composable
fun FilterButton(
    text: String,
    icon: @Composable () -> Unit,
    borderColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = 16.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = textColor
            )
        }
    }
}