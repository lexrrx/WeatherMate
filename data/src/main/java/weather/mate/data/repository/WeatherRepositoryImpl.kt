package weather.mate.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import weather.mate.data.model.WeatherResponse
import weather.mate.data.network.WeatherApiService
import weather.mate.domain.model.DailyWeatherData
import weather.mate.domain.model.WeatherData
import weather.mate.domain.repository.WeatherRepository
import java.time.LocalDate


internal class WeatherRepositoryImpl (private val apiService: WeatherApiService) : WeatherRepository {

    override suspend fun getWeather(latitude: String, longitude: String, daily: String): WeatherData = withContext(Dispatchers.IO) {
        apiService.getWeather(latitude, longitude, daily).toWeatherData()
    }
}

internal fun WeatherResponse.toWeatherData () : WeatherData {
    return WeatherData(
        latitude = this.latitude,
        longitude = this.longitude,
        elevation = this.elevation,
        dailyWeatherData = this.daily.time.mapIndexedNotNull { index, time ->
            DailyWeatherData(
                time = LocalDate.parse(time),
                temperatureMax = this.daily.temperatureMax.getOrNull(index) ?: return@mapIndexedNotNull null,
                temperatureMin = this.daily.temperatureMin.getOrNull(index) ?: return@mapIndexedNotNull null
            )
        }
    )
}