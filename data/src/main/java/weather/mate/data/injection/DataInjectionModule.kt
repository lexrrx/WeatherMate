package weather.mate.data.injection

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import weather.mate.domain.repository.WeatherRepository

val dataModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set the logging level
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

// Provide a Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


// Provide the WeatherApiService using the Retrofit instance
    single {
        get<Retrofit>().create(weather.mate.data.network.WeatherApiService::class.java)
    }

// Provide the WeatherRepository
    single <WeatherRepository> {
        weather.mate.data.repository.WeatherRepositoryImpl(get())
    }

}