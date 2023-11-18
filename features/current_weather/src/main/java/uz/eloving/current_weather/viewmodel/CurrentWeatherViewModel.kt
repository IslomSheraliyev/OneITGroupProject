package uz.eloving.current_weather.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.eloving.current_weather.usecase.CurrentWeatherUseCase
import uz.eloving.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.eloving.core_ui.PrefManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<Any>?>(null)
    val state: StateFlow<Resource<Any>?> = _state


    fun getCurrentWeather(location: String) = viewModelScope.launch {

        _state.value = Resource.Loading()

        val result = currentWeatherUseCase.getCurrentWeather(location)

        result.fold(
            onSuccess = {
                _state.value = Resource.Success(it)
            },
            onFailure = {
                _state.value = Resource.Error("Unknown Error", it)
            }
        )
    }

    fun parseDateToTime(time: String): String {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val outputSDF = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date: Date? = try {
            inputSDF.parse(time)
        } catch (e: ParseException) {
            return time
        }
        return outputSDF.format(date)

    }

}