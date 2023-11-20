package weather.mate

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import weather.mate.ui.NotificationPermission
import weather.mate.ui.components.HeaderControl
import weather.mate.ui.components.WeatherCard
import weather.mate.ui.theme.WeatherMateTheme
import weather.mate.weather.WeatherViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<WeatherViewModel>()

    private var service by mutableStateOf<WeatherService?>(null)

    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as WeatherService.LocalBinder
            this@MainActivity.service = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isServiceRunningPrefState.value) {
            super.onStart()
            Intent(this, WeatherService::class.java).also { intent ->
                bindService(intent, connection, BIND_AUTO_CREATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherMateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        NotificationPermission()
                        HeaderControl(
                            viewModel.isServiceRunningPrefState.value,
                            onClick = {
                                headerControlClickHandler(viewModel.isServiceRunningPrefState.value)
                            })
                        WeatherCard(
                            service?.weatherData?.collectAsState()?.value,
                            viewModel.isServiceRunningPrefState.value
                        )
                    }
                }
            }
        }
    }


    fun headerControlClickHandler(isServiceRunningPrefState: Boolean) {
        when (isServiceRunningPrefState) {
            true -> {
                stopAndUnbindWeatherService()
                viewModel.updateIsServiceRunningPrefState(false)
            }

            false -> {
                startAndBindWeatherService()
                viewModel.updateIsServiceRunningPrefState(true)

            }
        }
    }

    private fun startAndBindWeatherService() {
        val intent = Intent(applicationContext, WeatherService::class.java)
        ContextCompat.startForegroundService(this@MainActivity, intent)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    private fun stopAndUnbindWeatherService() {
        Intent(
            applicationContext,
            WeatherService::class.java
        ).also {
            stopService(it)
        }

        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}
