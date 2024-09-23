package com.luciano.vetconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.luciano.vetconnect.features.vet_detail.VetDetailScreen
import com.luciano.vetconnect.navigation.NavGraph
import com.luciano.vetconnect.shared.ui.theme.VetConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VetDetailScreen()
            //VetConnectTheme {
               // val navController = rememberNavController()
             //   NavGraph(navController = navController)
           // }

        }
    }
}

