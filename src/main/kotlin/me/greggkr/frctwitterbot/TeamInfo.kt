package me.greggkr.frctwitterbot

data class TeamInfo(val twitter: String,
                    val hour: Int,
                    val minute: Int,
                    val timezone: String = "America/Chicago"
)