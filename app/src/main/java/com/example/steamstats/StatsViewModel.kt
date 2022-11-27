package com.example.steamstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.steamstats.models.UserInfo
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL
import java.net.UnknownHostException

class StatsViewModel: ViewModel() {
    var error: Exception? = null
    var errorMessage = MutableLiveData("")
    lateinit var gamesInfoResponse: JSONObject
    lateinit var profileInfoResponse: JSONObject
    lateinit var user: UserInfo

    suspend fun search(steamID: String) {
        error = null
        viewModelScope.async(Dispatchers.IO) {
            try {
                gamesInfoResponse = JSONObject(URL("https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key=A82757CACA496C85F1F1B669FD6D19A5&steamid=$steamID&include_appinfo=true").readText())
                profileInfoResponse = JSONObject(URL("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=A82757CACA496C85F1F1B669FD6D19A5&steamids=$steamID").readText())
                if (gamesInfoResponse.getString("response") == "{}")
                    error = Exception("SteamID not found.")
            } catch (e: UnknownHostException) {
                error = Exception("No internet connection.")
            } catch (e: FileNotFoundException) {
                error = Exception("SteamID not valid.")
            }
        }.await()
        if (error != null) { errorMessage.value = error!!.message.toString() }
        else { createUser(steamID) }
    }

    private fun createUser(steamID: String) {
        var totalHours = 0
        var gamesList = gamesInfoResponse.getJSONObject("response").getJSONArray("games")
        for (i in 0 until gamesInfoResponse.getJSONObject("response").getInt("game_count")) {
            val game = gamesList.getJSONObject(i)
            totalHours += game.getInt("playtime_forever")
        }
        totalHours /= 60

        var userName = profileInfoResponse.getJSONObject("response").getJSONArray("players").getJSONObject(0).getString("personaname")
        var avatarURL = profileInfoResponse.getJSONObject("response").getJSONArray("players").getJSONObject(0).getString("avatarfull")
        user = UserInfo(steamID, userName, avatarURL, gamesList, totalHours)
    }

}