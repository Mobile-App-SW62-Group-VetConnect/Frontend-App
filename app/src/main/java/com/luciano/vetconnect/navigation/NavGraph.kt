package com.luciano.vetconnect.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.features.auth.password.ForgotPasswordScreen
import com.luciano.vetconnect.features.auth.login.LoginScreen
import com.luciano.vetconnect.features.auth.password.ChangePasswordScreen
import com.luciano.vetconnect.features.auth.register.RegisterScreen
import com.luciano.vetconnect.features.auth.splash.SplashScreen
import com.luciano.vetconnect.features.savedvet.SavedVetScreen
import com.luciano.vetconnect.features.vet_detail.VetDetailScreen
import com.luciano.vetconnect.shared.ui.components.MenuOverlay
import com.luciano.vetconnect.shared.ui.theme.*
import com.luciano.vetconnect.features.search.SearchScreen
import com.luciano.vetconnect.features.search.SearchResults
import com.luciano.vetconnect.features.home.HomeScreen
import com.luciano.vetconnect.features.notifications.NotificationsScreen
import com.luciano.vetconnect.features.profile.EditProfileScreen
import com.luciano.vetconnect.features.profile.ProfileScreen
import com.luciano.vetconnect.shared.ui.components.NotificationSettingsScreen
import com.luciano.vetconnect.shared.ui.components.SettingsScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object VetDetail : Screen("vet_detail")
    object Search : Screen("search")
    object SearchResults : Screen("searchResultScreen")
    object SavedVetScreen : Screen("favorites")
    object Settings : Screen("settings")
    object ForgotPassword : Screen("forgot_password")
    object EditProfile : Screen("edit_profile")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
    object ChangePassword : Screen("change_password")
    object NotificationSettings : Screen("notification_settings")
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
                    tint = BrandColors.Secondary,
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackgroundColors.Secondary
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
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

        composable(Screen.SavedVetScreen.route) {
            SavedVetScreen(
                navController = navController,
                onMenuClick = onMenuClick
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onMenuClick = onMenuClick
            )
        }

        composable(Screen.VetDetail.route) {
            VetDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable(Screen.SearchResults.route) {
            SearchResults(
                navController = navController,
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                onMenuClick = onMenuClick
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }
        // En NavGraph.kt
        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(navController = navController)
        }
        composable(Screen.NotificationSettings.route) {
            NotificationSettingsScreen(navController = navController)
        }
    }
}