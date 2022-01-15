package pl.org.seva.licznik.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.button {
            vm.counter.value++
        }
        binding.evenNumber.text = vm.counter.value.toString()

        lifecycleScope.launch {
            vm.counter
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { value ->
                    val vanish = if (value.mod(2) == 0) binding.oddNumber else binding.evenNumber
                    val appear = if (value.mod(2) == 0) binding.evenNumber else binding.oddNumber

                    vanish.animate().alpha(0.0F)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                vanish.visibility = View.INVISIBLE
                            }
                        })

                    appear.text = value.toString()
                    appear.visibility = View.VISIBLE
                    appear.animate().alpha(1.0F)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                appear.visibility = View.VISIBLE
                            }
                        })
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset -> {
                if (vm.counter.value % 2 == 0) {
                    binding.evenNumber.visibility = View.INVISIBLE
                    binding.oddNumber.text = vm.counter.value.toString()
                    binding.oddNumber.alpha = 1.0F
                    binding.oddNumber.visibility = View.VISIBLE
                }
                vm.counter.value = 0
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
