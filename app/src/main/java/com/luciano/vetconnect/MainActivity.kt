package com.luciano.vetconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.luciano.vetconnect.navigation.VetConnectApp
import com.luciano.vetconnect.shared.ui.theme.VetConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VetConnectTheme {
                VetConnectApp()
            }
        }
    }
}