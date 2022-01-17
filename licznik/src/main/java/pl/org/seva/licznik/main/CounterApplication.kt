package pl.org.seva.licznik.main

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors

@Suppress("unused")
class CounterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
