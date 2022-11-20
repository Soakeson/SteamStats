package com.example.steamstats
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.steamstats.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = LoginViewModel()
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener {
            if (viewModel.login(binding.userNameEntry.text.toString(), binding.passwordEntry.text.toString()))
                findNavController().navigate(R.id.login_to_stats)

        }
        return binding.root
    }
}