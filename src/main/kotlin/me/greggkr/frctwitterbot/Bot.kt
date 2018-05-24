package me.greggkr.frctwitterbot

interface Bot {
    /**
     * @param team A: team number, B: info related to that team
     *
     * Handles all of the tweeting functions
     */
    fun tweet(team: Pair<Int, TeamInfo>)
}