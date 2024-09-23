package com.luciano.vetconnect.features.savedvet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luciano.vetconnect.R
import com.luciano.vetconnect.shared.data.models.Veterinary
import com.luciano.vetconnect.shared.ui.theme.VetConnectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class SavedVetScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VetConnectTheme() {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Scaffold(
        topBar = {
            TopBar()
        },
        content = { padding ->
            BodyContent(modifier = Modifier.padding(padding))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1E4E3C))
    )
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    var veterinarias by remember { mutableStateOf(listOf<Veterinary>()) }
    LaunchedEffect(Unit) {
        veterinarias = fetchVeterinarias()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFF3E0))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(2.dp, Color(0xFF1ECD98), RoundedCornerShape(8.dp))
                    .background(Color.Transparent)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Filtros", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1ECD98))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(2.dp, Color(0xFF8E8E8E), RoundedCornerShape(8.dp))
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Ordenar por", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8E8E8E))
            }
        }
        Text(
            text = "Veterinarias guardadas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Column {
            veterinarias.forEach { veterinary ->
                VeterinaryCard(
                    name = veterinary.name,
                    address = veterinary.address,
                    rating = veterinary.rating,
                    clinicPrice = veterinary.clinicPrice,
                    bathPrice = veterinary.bathPrice
                )
            }
        }
    }
}

suspend fun fetchVeterinarias(): List<Veterinary> {
    return withContext(Dispatchers.IO) {
        val url = "https://mocki.io/v1/8b031a14-0dbe-473c-98a4-bf3113e712f8"
        val response = URL(url).readText()

        val jsonObject = org.json.JSONObject(response)

        val jsonArray = jsonObject.getJSONArray("veterinaryCards")

        val veterinarias = mutableListOf<Veterinary>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val veterinary = Veterinary(
                name = item.getString("name"),
                address = item.getString("address"),
                rating = item.getInt("rating"),
                clinicPrice = item.getString("clinicPrice"),
                bathPrice = item.getString("bathPrice")
            )
            veterinarias.add(veterinary)
        }
        veterinarias
    }
}

@Composable
fun VeterinaryCard(name: String, address: String, rating: Int, clinicPrice: String, bathPrice: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) { index ->
                    if (index < rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.veterinaria),
                    contentDescription = "Imagen de la veterinaria",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Dirección: $address", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Consulta clínica: $clinicPrice", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Baño de la mascota: $bathPrice", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    VetConnectTheme() {
        MyApp()
    }
}