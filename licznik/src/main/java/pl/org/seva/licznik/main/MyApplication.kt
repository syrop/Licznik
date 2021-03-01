package pl.org.seva.licznik.main

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

@Suppress("unused")
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
