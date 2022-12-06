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
import com.example.steamstats.databinding.FragmentProfileBinding
import com.example.steamstats.models.SteamID
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: StatsViewModel by activityViewModels()
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.profileUserName.text = viewModel.user!!.userName
        binding.profileTotalHoursPlayed.text = viewModel.user!!.totalHours.toString()
        binding.profileLastLogoff.text = viewModel.user!!.lastLogoff.toString()
        binding.profileTitlesOwned.text = viewModel.user!!.gamesList.size.toString()
        binding.profileBacklog.text = viewModel.user!!.backlog.size.toString()
        binding.profileDateCreated.text = viewModel.user!!.timeCreated.toString()
        binding.profileSteamId.text = viewModel.user!!.steamID.toString()

        Picasso
            .get()
            .load(viewModel.user!!.avatarURL)
            .into(binding.profileAvatar)

        binding.logoutButton.setOnClickListener {
            viewModel.steamIdFound.value = false
            findNavController().navigate(R.id.profile_to_login)
        }

        return binding.root
    }
}