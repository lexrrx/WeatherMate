package weather.mate.data.model

import com.google.gson.annotations.SerializedName

internal data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    @SerializedName("daily_units")
    val dailyUnits: DailyUnits,
    val daily: DailyWeatherData
)

internal data class DailyUnits(
    val time: String,
    @SerializedName("temperature_2m_max")
    val temperature2mMaxUnit: String
)

internal data class DailyWeatherData(
    val time: List<String>,
    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double>
)