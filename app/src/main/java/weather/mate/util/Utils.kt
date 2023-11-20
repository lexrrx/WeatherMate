package weather.mate.util

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun PerformOnLifecycleEvent(
    lifecycleOwner: LifecycleOwner,
    onStart: () -> Unit = {},
    onStop: () -> Unit = {}
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    onStart()
                    Log.i("OBSERVER", "Lifecycle: ${event.name}")
                }

                Lifecycle.Event.ON_STOP -> {
                    onStop()
                    Log.i("OBSERVER", "Lifecycle: ${event.name}")
                }

                else -> Log.i("OBSERVER", "Lifecycle: ${event.name}")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
