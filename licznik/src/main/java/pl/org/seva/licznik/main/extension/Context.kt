package pl.org.seva.licznik.main.extension

import android.content.Context
import android.content.SharedPreferences

val Context.prefs: SharedPreferences
    get() = getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE)
