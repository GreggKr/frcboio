package me.greggkr.frctwitterbot

data class TeamInfo(val twitter: String,
                    val hour: Int, // lazy, probably is a better way to do this
                    val minute: Int // ^
)