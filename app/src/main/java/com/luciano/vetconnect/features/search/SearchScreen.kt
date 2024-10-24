package com.luciano.vetconnect.features.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var recentSearches by remember { mutableStateOf(
        listOf("El Roble", "Baño y Corte de pelo", "Ricocat")
    )}

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = BrandColors.Secondary
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                            shape = MaterialTheme.shapes.medium,
                            color = BackgroundColors.Primary
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = null,
                                    tint = TextColors.Secondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                BasicTextField(
                                    value = searchText,
                                    onValueChange = {
                                        searchText = it
                                        if (it.isNotEmpty()) {
                                            navController.navigate(Screen.SearchResults.route)
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    decorationBox = { innerTextField ->
                                        Box {
                                            if (searchText.isEmpty()) {
                                                Text(
                                                    "¿Qué estás buscando?",
                                                    color = TextColors.Secondary,
                                                    fontSize = 16.sp
                                                )
                                            }
                                            innerTextField()
                                        }
                                    },
                                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                                        color = TextColors.Primary,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BackgroundColors.Secondary
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        containerColor = BackgroundColors.Primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Búsquedas Recientes
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Búsquedas Recientes",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = TextColors.Primary
                    )
                    TextButton(
                        onClick = { recentSearches = emptyList() }
                    ) {
                        Text(
                            text = "Limpiar Historial",
                            color = BrandColors.Primary,
                            fontSize = 14.sp
                        )
                    }
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 12.dp,
                    crossAxisSpacing = 12.dp
                ) {
                    recentSearches.forEach { search ->
                        RecentSearchChip(
                            text = search,
                            onChipClick = { navController.navigate(Screen.SearchResults.route) },
                            onDeleteClick = {
                                recentSearches = recentSearches.filter { it != search }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Servicios más buscados
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Servicios más buscados",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = TextColors.Primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 12.dp,
                    crossAxisSpacing = 12.dp
                ) {
                    listOf(
                        "Corte de uñas",
                        "Baño y Corte de pelo",
                        "Spa para gatos",
                        "Spa para perros"
                    ).forEach { service ->
                        SuggestionChip(
                            text = service,
                            onClick = { navController.navigate(Screen.SearchResults.route) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rows = mutableListOf<MutableList<androidx.compose.ui.layout.Placeable>>()
        val rowWidths = mutableListOf<Int>()
        val rowHeights = mutableListOf<Int>()

        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0
        var currentRowHeight = 0

        val mainAxisSpacingPx = mainAxisSpacing.roundToPx()
        val crossAxisSpacingPx = crossAxisSpacing.roundToPx()

        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentRowWidth + placeable.width +
                (if (currentRow.isEmpty()) 0 else mainAxisSpacingPx) > constraints.maxWidth) {
                rows.add(currentRow)
                rowWidths.add(currentRowWidth)
                rowHeights.add(currentRowHeight)

                currentRow = mutableListOf()
                currentRowWidth = 0
                currentRowHeight = 0
            }

            currentRow.add(placeable)
            currentRowWidth += placeable.width + (if (currentRow.size > 1) mainAxisSpacingPx else 0)
            currentRowHeight = maxOf(currentRowHeight, placeable.height)
        }

        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
            rowWidths.add(currentRowWidth)
            rowHeights.add(currentRowHeight)
        }

        val totalHeight = rowHeights.sumOf { it } +
                (rowHeights.size - 1).coerceAtLeast(0) * crossAxisSpacingPx

        layout(constraints.maxWidth, totalHeight) {
            var y = 0
            rows.forEachIndexed { i, row ->
                var x = 0
                row.forEach { placeable ->
                    placeable.place(x, y)
                    x += placeable.width + mainAxisSpacingPx
                }
                y += rowHeights[i] + crossAxisSpacingPx
            }
        }
    }
}

@Composable
private fun RecentSearchChip(
    text: String,
    onChipClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Surface(
        onClick = onChipClick,
        modifier = Modifier,
        shape = MaterialTheme.shapes.medium,
        color = BrandColors.Primary.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.History,
                contentDescription = null,
                tint = BrandColors.Primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                color = BrandColors.Primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Eliminar",
                    tint = BrandColors.Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun SuggestionChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier,
        shape = MaterialTheme.shapes.medium,
        color = BrandColors.Primary.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.NavigateNext,
                contentDescription = null,
                tint = BrandColors.Primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                color = BrandColors.Primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}