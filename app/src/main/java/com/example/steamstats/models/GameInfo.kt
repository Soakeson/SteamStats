package com.example.steamstats.models

data class GameInfo (
    var appid: Long,
    var title: String,
    var imgIconURL: String,
    var playTime: Int,
    var hasPlayed: Boolean
)