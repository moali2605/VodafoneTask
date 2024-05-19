package com.example.vodafonetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.city.CitySearchScreen
import com.example.current_weather.HomeScreen
import com.example.days_forecast.DaysTempScreen
import com.example.vodafonetask.ui.theme.VodafoneTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCreation()
        }
    }
}

@Composable
fun AppCreation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("citySearchScreen") { CitySearchScreen(navController) }
        composable("HomeScreen") { HomeScreen(navController) }
        composable("daysTempScreen") { DaysTempScreen() }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VodafoneTaskTheme {

    }
}