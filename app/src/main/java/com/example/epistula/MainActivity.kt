package com.example.epistula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.epistula.pages.HomePage
import com.example.epistula.pages.LoginPage
import com.example.epistula.pages.WelcomePage
import com.example.epistula.ui.theme.EpistulaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EpistulaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home" ){
                        composable(route = "login"){ LoginPage(navController) }
                        composable(route = "bemvindo" ){ WelcomePage(navController) }
                        composable(route = "home"){ HomePage(navController) }
                }
            }
        }
    }
}
}