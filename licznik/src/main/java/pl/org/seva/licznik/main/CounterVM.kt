package pl.org.seva.licznik.main

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.org.seva.licznik.main.extension.prefs

class CounterVM(
    application: Application,
    private val prefs: SharedPreferences,
) : AndroidViewModel(application) {

    @Suppress("unused")
    constructor(application: Application) : this(application, application.prefs)

    private val _counter = MutableStateFlow(prefs.getInt(STATE, 0))
    val counter: StateFlow<Int> = _counter.asStateFlow()

    fun increment() {
        val next = _counter.value + 1
        _counter.value = next
        prefs.edit {
            putInt(STATE, next)
        }
    }

    fun reset() {
        _counter.value = 0
        prefs.edit {
            putInt(STATE, 0)
        }
    }

    companion object {
        const val STATE = "state"
    }
}
