package com.example.steamstats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.steamstats.databinding.FragmentStatsBinding
import com.example.steamstats.models.UserInfo

class StatsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: StatsViewModel by activityViewModels()
        val user = UserInfo(viewModel.response)
        val binding = FragmentStatsBinding.inflate(inflater, container, false)
        binding.hoursPlayed.text = (user.totalHours).toString()

        return binding.root
    }
}