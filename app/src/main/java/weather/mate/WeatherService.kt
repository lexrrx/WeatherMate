package weather.mate

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import weather.mate.WeatherMateApplication.Companion.CHANNEL_ID
import weather.mate.domain.model.WeatherData
import weather.mate.domain.repository.WeatherRepository
import weather.mate.util.Constants
import weather.mate.util.REFRESH_RATE_INTERVAL
import weather.mate.util.SERVICE_IS_RUNNING
import weather.mate.util.SharedPreferencesUtil


class WeatherService() : Service() {
    private val weatherRepository: WeatherRepository by inject()

    private val sharedPreferencesUtil: SharedPreferencesUtil by inject()

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val binder = LocalBinder()

    private val _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData.asStateFlow()

    inner class LocalBinder : Binder() {
        fun getService(): WeatherService = this@WeatherService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

            var locationIndex = 0

            if (Build.VERSION.SDK_INT >= 34) {
                ServiceCompat.startForeground(
                    this,
                    NOTIFICATION_ID,
                    createNotification(locationIndex,
                        getString(R.string.starting_weather_service_notification_placeholder)),
                    FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
                )
            } else {
                ServiceCompat.startForeground(
                    this,
                    NOTIFICATION_ID,
                    createNotification(locationIndex, "Starting Weather Service..."),
                    0
                )
            }

            serviceScope.launch {

                sharedPreferencesUtil.setBoolean(SERVICE_IS_RUNNING, true)

                while (isActive) {

                    val currentLocation =
                        Constants.COORDINATES[locationIndex % Constants.COORDINATES.size]
                    val latitude = currentLocation.first
                    val longitude = currentLocation.second

                    _weatherData.value = weatherRepository.getWeather(
                        latitude,
                        longitude
                    )

                    val notification =
                        createNotification(
                            locationIndex,
                            getString(
                                R.string.notification_text,
                                locationIndex.toString(),
                                _weatherData.value?.latitude.toString(),
                                _weatherData.value?.longitude.toString(),
                                _weatherData.value?.dailyWeatherData?.get(0)?.temperatureMax.toString()
                            )
                        )

                    notificationManager.notify(NOTIFICATION_ID, notification)

                    locationIndex++
                    delay(REFRESH_RATE_INTERVAL)
                }
            }
    }

    private fun createNotification(locationIndex: Int = 0, contentText: String): Notification {
        val tapIntent = createTapNotificationIntent(locationIndex)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentTitle(getString(R.string.weather_service_notification_title))
            .setContentText(contentText)
            .setContentIntent(tapIntent)
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .build()
    }

    private fun createTapNotificationIntent(index: Int): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            action = Intent.ACTION_MAIN;
            addCategory(Intent.CATEGORY_LAUNCHER);
            putExtra(RETURNED_INDEX, index)
        }

        return PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferencesUtil.setBoolean(SERVICE_IS_RUNNING, false)
        serviceScope.cancel()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        const val RETURNED_INDEX = "extra_data"
    }
}