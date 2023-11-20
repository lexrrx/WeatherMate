package weather.mate.injection.module

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import weather.mate.util.SharedPreferencesUtil
import weather.mate.weather.WeatherViewModel

val appModule = module {
    viewModel {
        WeatherViewModel(get())
    }

    single {
        SharedPreferencesUtil(androidContext())
    }
}