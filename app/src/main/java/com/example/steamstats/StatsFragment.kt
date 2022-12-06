package com.example.steamstats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.steamstats.databinding.FragmentStatsBinding
import com.squareup.picasso.Picasso

class StatsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: StatsViewModel by activityViewModels()
        val binding = FragmentStatsBinding.inflate(inflater, container, false)
        binding.totalHoursPlayed.text = (viewModel.user!!.totalHours).toString()
        binding.userName.text = viewModel.user!!.userName
        Picasso
            .get()
            .load(viewModel.user!!.avatarURL)
            .into(binding.avatar)

        binding.backlog.adapter = StatsAdapter(viewModel.user!!.backlog)

        if (container != null) {
            binding.backlog.layoutManager = LinearLayoutManager(container.context)
        }

        binding.avatar.setOnClickListener{
            findNavController().navigate(R.id.stats_to_profile)
        }

        return binding.root
    }
}