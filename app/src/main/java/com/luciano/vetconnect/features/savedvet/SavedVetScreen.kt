package com.luciano.vetconnect.features.savedvet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.data.models.Veterinary
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.navigation.TopAppBar
import com.luciano.vetconnect.shared.ui.components.VetCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

enum class SortOption(val displayName: String) {
    NAME_ASC("Nombre (A-Z)"),
    NAME_DESC("Nombre (Z-A)"),
    RATING_HIGH("Mayor calificación"),
    RATING_LOW("Menor calificación"),
    PRICE_LOW("Menor precio"),
    PRICE_HIGH("Mayor precio")
}

data class FilterOptions(
    val minRating: Int = 0,
    val maxPrice: Int = Int.MAX_VALUE
)

@Composable
fun SavedVetScreen(navController: NavController, onMenuClick: () -> Unit) {
    val context = LocalContext.current
    var veterinaries by remember { mutableStateOf<List<Veterinary>>(emptyList()) }
    var filteredVeterinaries by remember { mutableStateOf<List<Veterinary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var showFilterDialog by remember { mutableStateOf(false) }
    var showSortDialog by remember { mutableStateOf(false) }
    var currentFilterOptions by remember { mutableStateOf(FilterOptions()) }
    var currentSortOption by remember { mutableStateOf(SortOption.NAME_ASC) }

    LaunchedEffect(Unit) {
        try {
            if (isNetworkAvailable(context)) {
                veterinaries = fetchVeterinarias()
                filteredVeterinaries = veterinaries
            } else {
                error = "No hay conexión a internet"
            }
        } catch (e: Exception) {
            error = "Error al cargar las veterinarias: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Efecto para aplicar filtros y ordenamiento
    LaunchedEffect(veterinaries, currentFilterOptions, currentSortOption) {
        filteredVeterinaries = veterinaries
            .filter { vet ->
                vet.rating >= currentFilterOptions.minRating &&
                        vet.clinicPrice.replace("[^0-9]".toRegex(), "").toIntOrNull()
                            ?.let { it <= currentFilterOptions.maxPrice } ?: true
            }
            .let { filtered ->
                when (currentSortOption) {
                    SortOption.NAME_ASC -> filtered.sortedBy { it.name }
                    SortOption.NAME_DESC -> filtered.sortedByDescending { it.name }
                    SortOption.RATING_HIGH -> filtered.sortedByDescending { it.rating }
                    SortOption.RATING_LOW -> filtered.sortedBy { it.rating }
                    SortOption.PRICE_LOW -> filtered.sortedBy {
                        it.clinicPrice.replace("[^0-9]".toRegex(), "").toIntOrNull() ?: 0
                    }
                    SortOption.PRICE_HIGH -> filtered.sortedByDescending {
                        it.clinicPrice.replace("[^0-9]".toRegex(), "").toIntOrNull() ?: 0
                    }
                }
            }
    }

    if (showFilterDialog) {
        FilterDialog(
            currentFilters = currentFilterOptions,
            onDismiss = { showFilterDialog = false },
            onApply = { newFilters ->
                currentFilterOptions = newFilters
                showFilterDialog = false
            }
        )
    }

    if (showSortDialog) {
        SortDialog(
            currentSort = currentSortOption,
            onDismiss = { showSortDialog = false },
            onSelect = { option ->
                currentSortOption = option
                showSortDialog = false
            }
        )
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
                            FilterButtons(
                                onFilterClick = { showFilterDialog = true },
                                onSortClick = { showSortDialog = true }
                            )
                            Text(
                                text = "Veterinarias guardadas",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkGreen,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                        items(filteredVeterinaries) { veterinary ->
                            VetCard(
                                veterinary = veterinary,
                                onVetClick = {
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
private fun FilterDialog(
    currentFilters: FilterOptions,
    onDismiss: () -> Unit,
    onApply: (FilterOptions) -> Unit
) {
    var minRating by remember { mutableStateOf(currentFilters.minRating) }
    var maxPrice by remember { mutableStateOf(currentFilters.maxPrice) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filtros") },
        text = {
            Column {
                Text("Calificación mínima")
                Slider(
                    value = minRating.toFloat(),
                    onValueChange = { minRating = it.toInt() },
                    valueRange = 0f..5f,
                    steps = 4
                )
                Text("Precio máximo")
                Slider(
                    value = maxPrice.toFloat().coerceIn(0f, 1000f),
                    onValueChange = { maxPrice = it.toInt() },
                    valueRange = 0f..1000f
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onApply(FilterOptions(minRating = minRating, maxPrice = maxPrice)) }
            ) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun SortDialog(
    currentSort: SortOption,
    onDismiss: () -> Unit,
    onSelect: (SortOption) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ordenar por") },
        text = {
            Column {
                SortOption.values().forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(option) }
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(option.displayName)
                        if (currentSort == option) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = PrimaryGreen
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun FilterButtons(
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = onFilterClick,
            modifier = Modifier
                .weight(1f)
                .border(2.dp, PrimaryGreen, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Filtros",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            onClick = onSortClick,
            modifier = Modifier
                .weight(1f)
                .border(2.dp, TextOptionGray, RoundedCornerShape(8.dp))
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Ordenar por",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextOptionGray
            )
        }
    }
}

suspend fun fetchVeterinarias(): List<Veterinary> {
    return withContext(Dispatchers.IO) {
        try {
            val url = "https://mocki.io/v1/34bccc77-5d0a-4787-bbbf-b6e107d439ab"
            val response = URL(url).readText()
            Log.d("VetConnect", "API Response: $response")  // Añade este log

            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("veterinaryCards")

            val veterinarias = mutableListOf<Veterinary>()
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val rating = item.getInt("rating")
                Log.d("VetConnect", "Veterinaria ${item.getString("name")} rating: $rating")  // Añade este log

                val veterinary = Veterinary(
                    name = item.getString("name"),
                    address = item.getString("address"),
                    rating = rating,
                    clinicPrice = item.getString("clinicPrice"),
                    bathPrice = item.getString("bathPrice")
                )
                veterinarias.add(veterinary)
            }
            veterinarias
        } catch (e: Exception) {
            Log.e("VetConnect", "Error fetching data: ${e.message}", e)
            throw e
        }
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