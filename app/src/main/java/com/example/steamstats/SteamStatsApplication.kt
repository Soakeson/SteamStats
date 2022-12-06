package com.example.steamstats

import android.app.Application
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class SteamStatsApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Application? = null

        fun getInstance(): Application {
            return instance!!
        }
    }
}