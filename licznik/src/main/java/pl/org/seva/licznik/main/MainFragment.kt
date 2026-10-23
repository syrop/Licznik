package pl.org.seva.licznik.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pl.org.seva.licznik.R
import pl.org.seva.licznik.databinding.FrMainBinding
import pl.org.seva.licznik.main.extension.invoke

class MainFragment : Fragment(R.layout.fr_main) {

    private val vm by viewModels<CounterVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FrMainBinding.bind(view)

        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.reset -> {
                            if ((vm.counter.value % 2) == 0) {
                                binding.evenNumber.animate().cancel()
                                binding.oddNumber.animate().cancel()
                                binding.evenNumber.visibility = View.INVISIBLE
                                binding.oddNumber.text = vm.counter.value.toString()
                                binding.oddNumber.alpha = 1.0F
                                binding.oddNumber.visibility = View.VISIBLE
                            }
                            vm.reset()
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top

            val params = binding.statusBarBackground.layoutParams as ConstraintLayout.LayoutParams
            params.height = statusBarHeight
            binding.statusBarBackground.layoutParams = params

            insets
        }

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            binding.button.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.google.android.material.R.color.material_dynamic_primary20,
                ),
            )
        }

        binding.button {
            vm.increment()
        }
        binding.evenNumber.text = vm.counter.value.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            vm.counter
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { value ->
                    val vanish = if (value.mod(2) == 0) binding.oddNumber else binding.evenNumber
                    val appear = if (value.mod(2) == 0) binding.evenNumber else binding.oddNumber

                    vanish.animate().cancel()
                    appear.animate().cancel()

                    vanish.animate().alpha(0.0F)
                        .setListener(
                            object : AnimatorListenerAdapter() {
                                private var isCanceled = false

                                override fun onAnimationCancel(animation: Animator) {
                                    isCanceled = true
                                }

                                override fun onAnimationEnd(animation: Animator) {
                                    if (!isCanceled) {
                                        vanish.visibility = View.INVISIBLE
                                    }
                                }
                            },
                        )

                    appear.text = value.toString()
                    appear.visibility = View.VISIBLE
                    appear.animate().alpha(1.0F)
                        .setListener(
                            object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    appear.visibility = View.VISIBLE
                                }
                            },
                        )
                }
        }
    }
}
