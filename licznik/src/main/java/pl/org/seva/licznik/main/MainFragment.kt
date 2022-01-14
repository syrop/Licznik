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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.skip
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
        binding.number.text = vm.counter.value.toString()

        lifecycleScope.launch {
            vm.counter
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { value ->
                    val vanish = if (value.mod(2) == 0) binding.number else binding.numberOdd
                    val appear = if (value.mod(2) == 0) binding.numberOdd else binding.number

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
                vm.counter.value = 0
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
