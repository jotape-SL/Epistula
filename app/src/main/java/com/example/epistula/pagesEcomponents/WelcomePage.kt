package com.example.epistula.pagesEcomponents
import android.content.Context
import com.example.epistula.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.epistula.ui.theme.DarkGray
import com.example.epistula.ui.theme.DarkGray80
import com.example.epistula.ui.theme.LightGray
import com.example.epistula.ui.theme.fontFamily

@Composable
fun WelcomePage(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") Color.White else DarkGray
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(majorColors)
    ) {
        // Background placeholder
        BackgroundStars()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ImageEye()

            Text(
                text = "Transforme sua experiência com e-mails. \n" +
                        "Com Epistula, leia e organize suas mensagens de forma inteligente e eficiente.\n\n" + "Bem-vindo a um novo nível de produtividade.",
                color = textColors,
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("login")},
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(textColors)
            ) {
                Text(
                    text = "Entrar",
                    color = majorColors,
                    fontSize = 25.sp ,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BackgroundStars() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val currentTheme = sharedPreferences.getString("app_theme", "light")
    val stars = if (currentTheme == "dark") R.drawable.estrelas_d else R.drawable.estrelas_l
    Image(
        painter = painterResource(id = stars),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ImageEye() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val currentTheme = sharedPreferences.getString("app_theme", "light")
    val eye = if (currentTheme == "dark") R.drawable.eye_d else R.drawable.eye_l
    Image(
        painter = painterResource(id = eye),
        contentDescription = "Partly Cloudy",
        modifier = Modifier
            .size(240.dp),
        alignment = Alignment.TopCenter,
    )
}

// Preview function
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    WelcomePage(navController = rememberNavController())
}



