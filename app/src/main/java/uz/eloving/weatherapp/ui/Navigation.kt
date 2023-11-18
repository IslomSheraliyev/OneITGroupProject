package uz.eloving.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.eloving.core_ui.NavigationItem
import uz.eloving.current_weather.view.CurrentWeatherScreen
import uz.eloving.forecasting.view.ForecastingScreen

@Composable
fun Navigation (navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.CurrentWeather.route){
        composable(NavigationItem.CurrentWeather.route){
            CurrentWeatherScreen(navController)
        }

        composable(NavigationItem.Forecasting.route){
            ForecastingScreen(navController)
        }

    }
}