package me.greggkr.frctwitterbot

const val teamAcct = "TWITTER_HANDLE"
const val time = "TIME_STRING"

val flavorTexts: ArrayList<String> = ArrayList()

fun getRandomFlavorText(team: TeamInfo): String {
    if (flavorTexts.isEmpty()) {
        flavorTexts.add("$teamAcct, it is $time")
        flavorTexts.add("It's $time, and you know what that means! Anyone here from $teamAcct?")
        flavorTexts.add("I'm thinking of a number between 100 and 1259. Someone on $teamAcct could probably guess it, given that it's $time.")
        flavorTexts.add("It's currently $time. There is an FRC team with that number: $teamAcct. Water game confirmed.")
        flavorTexts.add("I have to come up with a snarky comment about $teamAcct because it's $time. Anyone got any ideas?")
        flavorTexts.add("I wonder if anyone on $teamAcct is appreciating the fact that it's $time.")
        flavorTexts.add("Crap, it's $time already? Guess I need to bother $teamAcct.\n\nConsider yourself bothered.")
        flavorTexts.add("$teamAcct, what time is it in your time zone? It's $time here.")
    }

    val rawText = flavorTexts.get((Math.random() * flavorTexts.size).toInt())
    return rawText.replace(teamAcct, "@${team.twitter}").replace(time, "${team.hour}:${team.minute}")
}