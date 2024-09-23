package com.luciano.vetconnect.features.savedvet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.shared.data.models.Veterinary
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.navigation.TopAppBar
import com.luciano.vetconnect.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

@Composable
fun SavedVetScreen(navController: NavController, onMenuClick: () -> Unit) {
    val context = LocalContext.current
    var veterinaries by remember { mutableStateOf<List<Veterinary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            if (isNetworkAvailable(context)) {
                veterinaries = fetchVeterinarias()
            } else {
                error = "No hay conexión a internet"
            }
        } catch (e: Exception) {
            error = "Error al cargar las veterinarias: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopAppBar(onMenuClick = onMenuClick) },
        containerColor = SecondaryOrange2
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SecondaryOrange2)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = PrimaryGreen
                    )
                }
                error != null -> {
                    Text(
                        text = error!!,
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            FilterAndSortButtons()
                        }
                        item {
                            Text(
                                text = "Veterinarias guardadas",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkGreen,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            )
                        }
                        items(veterinaries) { veterinary ->
                            VeterinaryCard(
                                veterinary = veterinary,
                                onVeterinaryClick = {
                                    navController.navigate(Screen.VetDetail.route)
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterAndSortButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = { /* TODO: Implement filter logic */ },
            modifier = Modifier
                .weight(1f)
                .border(2.dp, PrimaryGreen, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Text("Filtros", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedButton(
            onClick = { /* TODO: Implement sort logic */ },
            modifier = Modifier
                .weight(1f)
                .border(2.dp, TextOptionGray, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Text("Ordenar por", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextOptionGray)
        }
    }
}

@Composable
fun VeterinaryCard(veterinary: Veterinary, onVeterinaryClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onVeterinaryClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = veterinary.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < veterinary.rating) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                        ),
                        contentDescription = null,
                        tint = SecondaryOrange,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.veterinaria),
                    contentDescription = "Imagen de la veterinaria",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Dirección: ${veterinary.address}", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Consulta clínica: ${veterinary.clinicPrice}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Baño de la mascota: ${veterinary.bathPrice}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

suspend fun fetchVeterinarias(): List<Veterinary> {
    return withContext(Dispatchers.IO) {
        val url = "https://mocki.io/v1/8b031a14-0dbe-473c-98a4-bf3113e712f8"
        val response = URL(url).readText()

        val jsonObject = JSONObject(response)
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

fun isNetworkAvailable(context: android.content.Context): Boolean {
    val connectivityManager = context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}