package com.luciano.vetconnect.features.auth.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luciano.vetconnect.R
import com.luciano.vetconnect.navigation.Screen
import com.luciano.vetconnect.shared.ui.theme.*

@Composable
fun RegisterScreen(navController: NavController) {
    var userType by remember { mutableStateOf("CLIENTE") }
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var documentoIdentidad by remember { mutableStateOf("") }
    var correoElectronico by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var confirmarContrasenaVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SecondaryOrange2
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Registrarse",
                style = MaterialTheme.typography.headlineMedium,
                color = TextOrange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { userType = "CLIENTE" },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userType == "CLIENTE") SecondaryGreen2 else SecondaryOrange2,
                        contentColor = if (userType == "CLIENTE") PrimaryWhite else LocalContentColor.current
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("CLIENTE")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { userType = "VETERINARIA" },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userType == "VETERINARIA") SecondaryGreen2 else SecondaryOrange2,
                        contentColor = if (userType == "VETERINARIA") PrimaryWhite else LocalContentColor.current
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("VETERINARIA")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = documentoIdentidad,
                onValueChange = { documentoIdentidad = it },
                label = { Text("Documento de Identidad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = correoElectronico,
                onValueChange = { correoElectronico = it },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                        Icon(
                            painter = painterResource(id = if (contrasenaVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                            contentDescription = if (contrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = TextOptionGray
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = if (confirmarContrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { confirmarContrasenaVisible = !confirmarContrasenaVisible }) {
                        Icon(
                            painter = painterResource(id = if (confirmarContrasenaVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                            contentDescription = if (confirmarContrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = TextOptionGray
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Implement registration logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryGreen2),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("REGISTRARSE")
            }

            Spacer(modifier = Modifier.height(30.dp))

            TextButton(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("¿Ya eres miembro?", color = TextOptionGray)
                Text(" Iniciar Sesión", color = TextGreen)
            }
        }
    }
}