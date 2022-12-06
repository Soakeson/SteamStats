package com.example.steamstats
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.steamstats.databinding.FragmentLoginBinding
import com.example.steamstats.models.SteamID
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: StatsViewModel by activityViewModels()
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel.steamIdFound.observe(viewLifecycleOwner) { steamIdFound ->
            if (steamIdFound && viewModel.user != null) {
                findNavController().navigate(R.id.login_to_stats)
            }
        }

        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.search(binding.steamIDEntry.text.toString())
                if (viewModel.error == null)
                    findNavController().navigate(R.id.login_to_stats)
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) {errorMessage ->
                binding.errorOutput.text = errorMessage
            }
        }
        return binding.root
    }
}