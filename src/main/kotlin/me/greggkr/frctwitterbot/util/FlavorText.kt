package me.greggkr.frctwitterbot.util

const val teamAcct = "TWITTER_HANDLE"
const val time = "TIME_STRING"

val flavorTexts = listOf(
        ".$teamAcct, it is $time",
        "It's $time, and you know what that means! Anyone here from $teamAcct?",
        "I'm thinking of a number between 100 and 1259. Someone on $teamAcct could probably guess it, given that it's $time.",
        "It's currently $time. There is an FRC team with that number: $teamAcct. Water game confirmed.",
        "I have to come up with a snarky comment about $teamAcct because it's $time. Anyone got any ideas?",
        "I wonder if anyone on $teamAcct is appreciating the fact that it's $time.",
        "Crap, it's $time already? Guess I need to bother $teamAcct.\n\nConsider yourself bothered.",
        ".$teamAcct, what time is it in your time zone? It's $time here."
)

fun getRandomFlavorText(team: TeamInfo): String {
    val rawText = flavorTexts[(Math.random() * flavorTexts.size).toInt()]

    return rawText
            .replace(teamAcct, "@${team.twitter}")
            .replace(time, "${team.hour}:${team.minute}")
}