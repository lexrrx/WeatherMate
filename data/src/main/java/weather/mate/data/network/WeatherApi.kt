package weather.mate.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import weather.mate.data.model.WeatherResponse

internal interface WeatherApiService {
    @GET("/v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("daily") daily: String
    ): WeatherResponse // Replace with your data model class
}
