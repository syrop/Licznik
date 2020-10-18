package pl.org.seva.licznik.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fr_main.*
import pl.org.seva.licznik.R
import pl.org.seva.licznik.main.extension.invoke

class MainFragment : Fragment(R.layout.fr_main) {

    private val vm by viewModels<VM>()

    var counter = 0
    set(value) {
        field = value
        number.text = field.toString()
        vm.counter = value
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        counter = vm.counter
        button {
            counter++
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset -> {
                counter = 0
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class VM : ViewModel() {
        var counter = 0
    }
}
