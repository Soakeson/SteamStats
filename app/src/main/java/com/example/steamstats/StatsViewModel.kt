package com.example.steamstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.net.URL
import java.net.UnknownHostException

class StatsViewModel: ViewModel() {
    var error: Exception? = null
    var errorMessage = MutableLiveData("")
    var response: String = ""
    var steamID: String = ""

    suspend fun search(id: String) {
        error = null
        viewModelScope.async(Dispatchers.IO) {
            try {
                steamID = id
                response = URL("https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key=A82757CACA496C85F1F1B669FD6D19A5&steamid=$steamID&include_appinfo=true").readText()
                if (response == "{\"response\":{}}")
                    error = Exception("SteamID not found.")
            } catch (e: UnknownHostException) {
                error = Exception("No internet connection.")
            } catch (e: FileNotFoundException) {
                error = Exception("SteamID not found.")
            }
        }.await()
        if (error != null) {
            errorMessage.value = error!!.message.toString()
        }


    }
    }
