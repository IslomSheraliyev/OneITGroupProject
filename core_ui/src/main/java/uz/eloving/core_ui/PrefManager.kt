package uz.eloving.core_ui

import android.content.Context
import android.content.SharedPreferences

const val CACHE = "APP"
const val CURRENT_LOCATION = "CURRENT_LOCATION"

class PrefManager {
    companion object {
        private fun getInstance(context: Context): SharedPreferences {
            return context.getSharedPreferences(CACHE, Context.MODE_PRIVATE)
        }

        fun getCurrentLocation(context: Context): String {
            return getInstance(context).getString("CURRENT_LOCATION", "Tashkent").toString()
        }

        fun setCurrentLocation(context: Context, newLocation: String) {
            getInstance(context).edit().putString(CURRENT_LOCATION, newLocation).apply()
        }
    }
}