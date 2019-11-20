package thefun.bakery

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import thefun.bakery.api.ApiClient

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()

        val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        val apiClient = ApiClient(pref)
    }
}