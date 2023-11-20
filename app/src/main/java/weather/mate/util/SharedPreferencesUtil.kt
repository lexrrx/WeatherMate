package weather.mate.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        prefs.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        prefs.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultValue: String = ""): String? {
        return prefs.getString(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        prefs.edit().apply {
            putInt(key, value)
            apply()
        }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return prefs.getInt(key, defaultValue)
    }

    // Add similar methods for other data types as needed

    fun clear() {
        prefs.edit().apply {
            clear()
            apply()
        }
    }

    fun remove(key: String) {
        prefs.edit().apply {
            remove(key)
            apply()
        }
    }
}