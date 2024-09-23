package com.luciano.vetconnect.features.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SecondaryOrange2 // Fondo beige
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.vet_connect_logo),
                contentDescription = "Vet Connect Logo",
                modifier = Modifier.size(300.dp) // Aumentado el tamaño del logo
            )

            Spacer(modifier = Modifier.height(48.dp)) // Aumentado el espacio después del logo

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = Black) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = TextOptionGray
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = TextStyle(color = TextOptionGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña",color = Black) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = TextOptionGray
                ),
                textStyle = TextStyle(color = TextOptionGray),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = PrimaryGreen
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Screen.VetDetail.route) },

                colors = ButtonDefaults.buttonColors(containerColor = SecondaryGreen2),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("INICIAR SESIÓN", color = PrimaryWhite)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { /* TODO: Implement forgot password navigation */ }) {
                Text("¿Olvidaste tu contraseña?", color = SecondaryGreen2)
            }

            TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text("Crear una cuenta", color = SecondaryGreen2)
            }
            }
        }
    }
