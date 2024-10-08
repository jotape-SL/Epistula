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
import com.example.epistula.objects.RetrofitClient
import com.example.epistula.pagesEcomponents.CalendarPage
import com.example.epistula.pagesEcomponents.HomePage
import com.example.epistula.pagesEcomponents.LoginPage
import com.example.epistula.pagesEcomponents.WelcomePage
import com.example.epistula.service.ApiService
import com.example.epistula.ui.theme.EpistulaTheme

class MainActivity : ComponentActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        loadAppTheme()
        super.onCreate(savedInstanceState)
        setContent {
            EpistulaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "calendario" ){
                        composable(route = "login"){ LoginPage(navController) }
                        composable(route = "bemvindo" ){ WelcomePage(navController) }
                        composable(route = "home"){ HomePage(navController, apiService) }
                        composable(route = "calendario"){ CalendarPage(navController) }
                }
            }
        }
    }
}

    fun setAppTheme(theme: String) {
        val editor = getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
        editor.putString("app_theme", theme)
        editor.apply()
    }
    private fun loadAppTheme() {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val currentTheme = sharedPreferences.getString("app_theme", "light")

        if (currentTheme == "dark") {
            setTheme(R.style.Theme_Epistula_Dark)
        } else {
            setTheme(R.style.Theme_Epistula_Light)
        }
    }
}