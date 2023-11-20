package weather.mate.ui

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission() {
    val context = LocalContext.current

    val notificationPermissionState: PermissionState

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        notificationPermissionState = rememberPermissionState(
            Manifest.permission.POST_NOTIFICATIONS
        )

        LaunchedEffect(Unit) {
            notificationPermissionState.launchPermissionRequest()
        }

        when {
            notificationPermissionState.status.isGranted -> {
                // Permission is granted
            }

            notificationPermissionState.status.shouldShowRationale -> {
                // Explain why we need the permission
            }

            !notificationPermissionState.status.isGranted -> {
                Toast.makeText(
                    context,
                    "Background tracking is not possible without notification permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}