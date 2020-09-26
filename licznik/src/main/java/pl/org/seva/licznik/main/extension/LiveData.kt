package pl.org.seva.licznik.main.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

operator fun <T> LiveData<T>.invoke(owner: LifecycleOwner, observer: (T) -> Unit) =
        observe(owner) { observer(it) }

operator fun <T> LiveData<T>.plus(owner: LifecycleOwner): ((T) -> Unit) -> Unit = { invoke(owner, it) }
