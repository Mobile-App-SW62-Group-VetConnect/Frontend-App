package com.luciano.vetconnect.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.R

@Composable
fun HomeScreen(navController: NavController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF3E0)
    )  {
        Column (verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(start = 20.dp, end = 20.dp).verticalScroll(
            rememberScrollState())){
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Clinicas veterinarias cerca de ti", fontSize = 20.sp, color = Color(0xFF000000))
            Spacer(modifier = Modifier.height(4.dp))
            VetCenterCard()
            VetCenterCard()
            VetCenterCard()
            VetCenterCard()
            Spacer(modifier = Modifier.height(4.dp))
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