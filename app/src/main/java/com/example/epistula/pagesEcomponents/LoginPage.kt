package com.example.epistula.pagesEcomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.epistula.R
import com.example.epistula.ui.theme.EpistulaTheme
import com.example.epistula.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {

    var text by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val placeholderEmail = "exemplo@exemplo.com"
    val placeholderSenha = ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333333))
    ) {
        BackgroundImages(R.drawable.estrelas_fade)
        BackgroundImages(R.drawable.eye_big)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Bem vindo de volta!",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email TextField
            Column {
                //Email TextField
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(imageResId = R.drawable.icon_email)
                    },
                    placeholder = {
                        if (text.isEmpty()) {
                            Text(placeholderEmail, color = Color.Gray)
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF000000), RoundedCornerShape(8.dp))
                        .padding(1.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password TextField
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon = if (passwordHidden) Icon(R.drawable.icon_visibility_off) else Icon(R.drawable.icon_visibility_on)
                            val description = if (passwordHidden) "Show password" else "Hide password"
                        }
                    },
                    leadingIcon = {
                        Icon(imageResId = R.drawable.icon_senha)
                    },
                    placeholder = {
                        if (text.isEmpty()) {
                            Text(placeholderSenha, color = Color.Gray)
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF000000), RoundedCornerShape(8.dp))
                        .padding(1.dp)
                )

                Text(
                    text = "Esqueceu sua senha?",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login Button
            Button(
                onClick = { navController.navigate("home")},
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 110.dp)
            ) {
                Text(
                    text = "Log in",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 24.sp,
                    fontFamily = fontFamily
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                )

                Text(
                    text = "Ou continuar com",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Sign-In Button
            Button(
                onClick = { /*TODO: Google sign-in event*/ },
                shape = RoundedCornerShape(15),
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.buttonColors(Color(0x45FFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(imageResId = R.drawable.icon_google)

                Text(
                    text = "Google",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    fontFamily = fontFamily
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Text(
                    text = "Não tem uma conta? ",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = fontFamily
                )
                Text(
                    text = "Registrar",
                    color = Color(0xFFDA5E4F),
                    fontSize = 14.sp,
                    fontFamily = fontFamily
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun Icon(imageResId: Int) {
    // Placeholder for the eye image
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Partly Cloudy",
        modifier = Modifier
            .size(24.dp),
        alignment = Alignment.TopCenter,
    )
}

@Composable
fun BackgroundImages(imageResId: Int) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    EpistulaTheme {
        LoginPage(navController = rememberNavController())
    }
}