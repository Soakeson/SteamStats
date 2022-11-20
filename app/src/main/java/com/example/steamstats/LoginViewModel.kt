package com.example.steamstats

import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    fun login(userName: String, password: String): Boolean {
        // Steam API stuff here
        println(userName + " : " + password)
        return true
    }
}