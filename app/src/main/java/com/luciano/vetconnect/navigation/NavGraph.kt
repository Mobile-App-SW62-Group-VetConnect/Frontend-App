package com.luciano.vetconnect.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.features.auth.login.LoginScreen
import com.luciano.vetconnect.features.auth.register.RegisterScreen
import com.luciano.vetconnect.features.auth.splash.SplashScreen

import com.luciano.vetconnect.features.savedvet.MyApp
import com.luciano.vetconnect.features.savedvet.SavedVetScreen

import com.luciano.vetconnect.features.vet_detail.VetDetailScreen
import com.luciano.vetconnect.shared.ui.components.MenuOverlay
import com.luciano.vetconnect.shared.ui.theme.SecondaryOrange
import com.luciano.vetconnect.features.home.HomeScreen


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home: Screen("home")
    object VetDetail : Screen("vet_detail")

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onMenuClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Icono de la app",
                modifier = Modifier.size(180.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Abrir menú",
                    tint = SecondaryOrange,
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E4E3C))
    )
}

@Composable
fun VetConnectApp() {
    val navController = rememberNavController()
    var isMenuOpen by remember { mutableStateOf(false) }

    Box {
        NavGraph(
            navController = navController,
            onMenuClick = { isMenuOpen = true }
        )
        MenuOverlay(
            isOpen = isMenuOpen,
            onClose = { isMenuOpen = false },
            onNavigate = { route ->
                isMenuOpen = false
                navController.navigate(route)
            }
        )
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route,
    onMenuClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onMenuClick = onMenuClick)
        }
        composable(Screen.VetDetail.route) {
            VetDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick
            )

        }

    }
}


