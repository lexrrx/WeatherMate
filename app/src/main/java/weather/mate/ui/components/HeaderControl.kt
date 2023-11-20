package weather.mate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import weather.mate.R

@Composable
fun HeaderControl(
    isTrackingEnabled: Boolean,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large.copy(
                    topStart = CornerSize(0),
                    topEnd = CornerSize(0)
                )
            )
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val weight = if (isTrackingEnabled) FontWeight.Normal else FontWeight.Light
        Text(
            text = if (isTrackingEnabled) stringResource(R.string.stop_tracking_me) else stringResource(
                R.string.track_me),
            color = if (isTrackingEnabled) Color.Red else Color.Green,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick() }
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 24.dp, vertical = 8.dp),
            fontWeight = weight
        )
    }
}