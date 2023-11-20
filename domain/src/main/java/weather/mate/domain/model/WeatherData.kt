package weather.mate.domain.model

import java.time.LocalDate

data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    val dailyWeatherData: List<DailyWeatherData>
)

data class DailyWeatherData(
    val time : LocalDate,
    val temperatureMax : Double,
    val temperatureMin: Double
)