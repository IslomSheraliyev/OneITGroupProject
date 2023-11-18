package uz.eloving.forecasting.usecase

import uz.eloving.data.repository.CurrentWeatherRepository
import javax.inject.Inject

class ForecastingUseCase @Inject constructor(private val repository : CurrentWeatherRepository) {

    suspend fun getForecastingDays() = repository.getForecasting("10")

}