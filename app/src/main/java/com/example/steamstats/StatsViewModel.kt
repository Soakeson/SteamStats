package com.example.steamstats

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.steamstats.models.GameInfo
import com.example.steamstats.models.SteamID
import com.example.steamstats.models.UserInfo
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL
import java.net.UnknownHostException

class StatsViewModel: ViewModel() {
    var error: Exception? = null
    var errorMessage = MutableLiveData("")
    var steamIdFound = MutableLiveData<Boolean>(false)
    var loggedID: SteamID? = null
    private lateinit var gamesInfoResponse: JSONObject
    private lateinit var profileInfoResponse: JSONObject
    var user: UserInfo? = null

    init {
        loadSteamID()
    }

    private fun loadSteamID() {
        viewModelScope.launch {
            loggedID = SteamStatsRepository.getSteamID()
            if (loggedID != null) {
                if (loggedID!!.steamID != "")
                    search(loggedID!!.steamID)
                    steamIdFound.value = true
            } else {
                SteamStatsRepository.createSteamId(SteamID(id = 0, ""))
                loggedID = SteamStatsRepository.getSteamID()
            }
        }
    }

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
        var gamesJSONList = gamesInfoResponse.getJSONObject("response").getJSONArray("games")
        var library = ObservableArrayList<GameInfo>()
        var backlog = ObservableArrayList<GameInfo>()
        for (i in 0 until gamesInfoResponse.getJSONObject("response").getInt("game_count")) {
            val game = gamesJSONList.getJSONObject(i)
            val playTime = game.getInt("playtime_forever")
            val hasPlayed = playTime > 0
            val gameInfo = GameInfo(
                game.getLong("appid"),
                game.getString("name"),
                "https://media.steampowered.com/steamcommunity/public/images/apps/${game.getString("appid")}/${game.getString("img_icon_url")}.jpg",
                game.getInt("playtime_forever"),
                hasPlayed
                )
            totalHours += playTime
            library.add(gameInfo)
            if (!hasPlayed) {
                backlog.add(gameInfo)
            }
        }
        totalHours /= 60

        var userName = profileInfoResponse.getJSONObject("response").getJSONArray("players").getJSONObject(0).getString("personaname")
        var avatarURL = profileInfoResponse.getJSONObject("response").getJSONArray("players").getJSONObject(0).getString("avatarfull")
        var lastLogoff = profileInfoResponse.getJSONObject("response").getJSONArray("players").getJSONObject(0).getLong("lastlogoff")
        var timecreated = profileInfoResponse.getJSONObject("response").getJSONArray("players").getJSONObject(0).getLong("timecreated")
        user = UserInfo(steamID, userName, avatarURL, library, backlog, totalHours, lastLogoff, timecreated)

        viewModelScope.launch { SteamStatsRepository.updateSteamID(SteamID(loggedID!!.id, steamID)) }
    }

}