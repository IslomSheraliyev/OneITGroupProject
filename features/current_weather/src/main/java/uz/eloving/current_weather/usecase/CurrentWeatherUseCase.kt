package uz.eloving.current_weather.usecase

import uz.eloving.data.model.CurrentWeather
import uz.eloving.data.repository.CurrentWeatherRepository
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(private val repositories: CurrentWeatherRepository) {
    suspend fun getCurrentWeather(location: String): Result<CurrentWeather> = repositories.getCurrentWeather(location)

}