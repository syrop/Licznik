package pl.org.seva.licznik.main

import android.app.Application
import com.google.android.material.color.DynamicColors

@Suppress("unused")
class CounterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
