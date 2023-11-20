package weather.mate.weather

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import weather.mate.util.SERVICE_IS_RUNNING
import weather.mate.util.SharedPreferencesUtil

class WeatherViewModel(private val sharedPreferencesUtil: SharedPreferencesUtil) : ViewModel() {

    var isServiceRunningPrefState = mutableStateOf(sharedPreferencesUtil.getBoolean(SERVICE_IS_RUNNING, false))
        private set

    fun updateIsServiceRunningPrefState(newValue: Boolean) {
        isServiceRunningPrefState.value = newValue
        sharedPreferencesUtil.setBoolean(SERVICE_IS_RUNNING, newValue)
    }
}