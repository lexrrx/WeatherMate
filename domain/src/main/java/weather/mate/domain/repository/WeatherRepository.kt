package weather.mate.domain.repository

import weather.mate.domain.model.WeatherData

interface WeatherRepository {

    suspend fun getWeather(
        latitude: String,
        longitude: String,
        daily: String = "temperature_2m_max,temperature_2m_min"): WeatherData
}
