package weather.mate.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import weather.mate.R
import weather.mate.domain.model.WeatherData

@Composable
fun WeatherCard(
    data: WeatherData?,
    isServiceRunning: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(8.dp)
    ) {
        if (isServiceRunning) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.Center)
            ) {
                data?.let { data ->
                    Text(
                        text = stringResource(
                            R.string.location_latitude_longitude,
                            data.latitude,
                            data.longitude
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(R.string.elevation_meters, data.elevation), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(R.string.daily_weather_data), fontWeight = FontWeight.Bold)
                    data.dailyWeatherData.forEach { daily ->
                        Text(
                            text = stringResource(
                                R.string.min_c_max_c,
                                daily.time,
                                daily.temperatureMin,
                                daily.temperatureMax
                            ),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else {
            Text(
                text = stringResource(R.string.enable_tracking_to_display_data),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}