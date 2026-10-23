package pl.org.seva.licznik.main

import android.app.Application
import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Test

class CounterVMTest {

    @Test
    fun `initial counter value defaults to 0 when preferences are empty`() {
        val prefs = FakeSharedPreferences()
        val vm = CounterVM(Application(), prefs)

        assertEquals(0, vm.counter.value)
    }

    @Test
    fun `initial counter value is loaded from SharedPreferences`() {
        val prefs = FakeSharedPreferences(mapOf(CounterVM.STATE to 42))
        val vm = CounterVM(Application(), prefs)

        assertEquals(42, vm.counter.value)
    }

    @Test
    fun `increment increases counter value and persists to SharedPreferences`() {
        val prefs = FakeSharedPreferences()
        val vm = CounterVM(Application(), prefs)

        vm.increment()
        assertEquals(1, vm.counter.value)
        assertEquals(1, prefs.getInt(CounterVM.STATE, -1))

        vm.increment()
        assertEquals(2, vm.counter.value)
        assertEquals(2, prefs.getInt(CounterVM.STATE, -1))
    }

    @Test
    fun `reset sets counter value to 0 and persists to SharedPreferences`() {
        val prefs = FakeSharedPreferences(mapOf(CounterVM.STATE to 15))
        val vm = CounterVM(Application(), prefs)

        vm.reset()

        assertEquals(0, vm.counter.value)
        assertEquals(0, prefs.getInt(CounterVM.STATE, -1))
    }

    private class FakeSharedPreferences(
        initialValues: Map<String, Any?> = emptyMap(),
    ) : SharedPreferences {
        private val data = initialValues.toMutableMap()

        override fun getAll(): MutableMap<String, *> = data.toMutableMap()

        override fun getString(key: String?, defValue: String?): String? =
            (data[key] as? String) ?: defValue

        @Suppress("UNCHECKED_CAST")
        override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? =
            (data[key] as? MutableSet<String>) ?: defValues

        override fun getInt(key: String?, defValue: Int): Int =
            (data[key] as? Int) ?: defValue

        override fun getLong(key: String?, defValue: Long): Long =
            (data[key] as? Long) ?: defValue

        override fun getFloat(key: String?, defValue: Float): Float =
            (data[key] as? Float) ?: defValue

        override fun getBoolean(key: String?, defValue: Boolean): Boolean =
            (data[key] as? Boolean) ?: defValue

        override fun contains(key: String?): Boolean = data.containsKey(key)

        override fun edit(): SharedPreferences.Editor = EditorImpl()

        override fun registerOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener?,
        ) = Unit

        override fun unregisterOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener?,
        ) = Unit

        private inner class EditorImpl : SharedPreferences.Editor {
            private val edits = mutableMapOf<String, Any?>()
            private var clearRequested = false

            override fun putString(key: String, value: String?): SharedPreferences.Editor = apply {
                edits[key] = value
            }

            override fun putStringSet(
                key: String,
                values: MutableSet<String>?,
            ): SharedPreferences.Editor = apply {
                edits[key] = values
            }

            override fun putInt(key: String, value: Int): SharedPreferences.Editor = apply {
                edits[key] = value
            }

            override fun putLong(key: String, value: Long): SharedPreferences.Editor = apply {
                edits[key] = value
            }

            override fun putFloat(key: String, value: Float): SharedPreferences.Editor = apply {
                edits[key] = value
            }

            override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor = apply {
                edits[key] = value
            }

            override fun remove(key: String): SharedPreferences.Editor = apply {
                edits[key] = null
            }

            override fun clear(): SharedPreferences.Editor = apply {
                clearRequested = true
            }

            override fun commit(): Boolean {
                apply()
                return true
            }

            override fun apply() {
                if (clearRequested) {
                    data.clear()
                }
                for ((key, value) in edits) {
                    if (value == null) {
                        data.remove(key)
                    } else {
                        data[key] = value
                    }
                }
            }
        }
    }
}
