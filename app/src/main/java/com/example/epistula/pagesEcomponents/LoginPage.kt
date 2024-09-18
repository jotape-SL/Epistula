package com.example.epistula.pagesEcomponents

import android.content.Context
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.epistula.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }

    var text by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val placeholderEmail = "exemplo@exemplo.com"
    val placeholderSenha = ""

    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") Color.White else DarkGray
    val textFieldColors = if (currentTheme == "dark") DarkGray80 else LightGray60
    val googleColor = if (currentTheme == "dark") translucidWhite else translucidBlack
    val eye = if (currentTheme == "dark") R.drawable.eye_big_d else R.drawable.eye_big_l
    val stars = if (currentTheme == "dark") R.drawable.estrelas_fade_d else R.drawable.estrelas_fade_l
    val google = if (currentTheme == "dark") R.drawable.icon_google_d else R.drawable.icon_google_l
    val email = if (currentTheme == "dark") R.drawable.icon_email_d else R.drawable.icon_email_l
    val senha = if (currentTheme == "dark") R.drawable.icon_senha_d else R.drawable.icon_senha_l
    val visualizacaoOn = if (currentTheme == "dark") R.drawable.icon_visibility_on_d else R.drawable.icon_visibility_on_l
    val visualizacaoOff = if (currentTheme == "dark") R.drawable.icon_visibility_off_d else R.drawable.icon_visibility_off_l

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(majorColors)
    ) {
        BackgroundImages(stars)
        BackgroundImages(eye)
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
                color = textColors,
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
                    label = { Text("Email", color = majorColors) },
                    leadingIcon = {
                        Icon(imageResId = email)
                    },
                    placeholder = {
                        if (text.isEmpty()) {
                            Text(placeholderEmail, color = majorColors)
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textFieldColors,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textColors, RoundedCornerShape(8.dp))
                        .padding(1.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password TextField
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha", color = majorColors) },
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon = if (passwordHidden) Icon(visualizacaoOff) else Icon(visualizacaoOn)
                            val description = if (passwordHidden) "Show password" else "Hide password"
                        }
                    },
                    leadingIcon = {
                        Icon(imageResId = senha)
                    },
                    placeholder = {
                        if (text.isEmpty()) {
                            Text(placeholderSenha, color = majorColors)
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textFieldColors,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textColors, RoundedCornerShape(8.dp))
                        .padding(1.dp)
                )

                Text(
                    text = "Esqueceu sua senha?",
                    color = textColors,
                    fontSize = 18.sp,
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
                colors = ButtonDefaults.buttonColors(textColors),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 110.dp)
            ) {
                Text(
                    text = "Log in",
                    color = majorColors,
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
                    color = textColors,
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                )

                Text(
                    text = "Ou continuar com",
                    color = textColors,
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Divider(
                    color = textColors,
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
                border = BorderStroke(1.dp, textColors),
                colors = ButtonDefaults.buttonColors(googleColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Icon(imageResId = google)

                Text(
                    text = "Google",
                    color = textColors,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                    fontFamily = fontFamily
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Text(
                    text = "Não tem uma conta? ",
                    color = textColors,
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