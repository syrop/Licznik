package pl.org.seva.licznik.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CounterVM : ViewModel() {

    val counter = MutableStateFlow(0)
}
