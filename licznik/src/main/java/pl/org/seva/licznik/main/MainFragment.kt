package pl.org.seva.licznik.main

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.org.seva.licznik.R
import pl.org.seva.licznik.databinding.FrMainBinding
import pl.org.seva.licznik.main.extension.invoke

class MainFragment : Fragment(R.layout.fr_main) {

    private val vm by viewModels<CounterVM>()

    private lateinit var binding: FrMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(true)
        binding = FrMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.button {
            vm.counter.value++
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset -> {
                vm.counter.value = 0
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
