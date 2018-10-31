package me.greggkr.frctwitterbot.util

const val ACCOUNT = "TWITTER_HANDLE"
const val TIME = "TIME_STRING"

val flavorTexts = listOf(
        ".$ACCOUNT, it is $TIME",
        "It's $TIME, and you know what that means! Anyone here from $ACCOUNT?",
        "I'm thinking of a number between 100 and 1259. Someone on $ACCOUNT could probably guess it, given that it's $TIME.",
        "It's currently $TIME. There is an FRC team with that number: $ACCOUNT. Water game confirmed.",
        "I have to come up with a snarky comment about $ACCOUNT because it's $TIME. Anyone got any ideas?",
        "I wonder if anyone on $ACCOUNT is appreciating the fact that it's $TIME.",
        "Crap, it's $TIME already? Guess I need to bother $ACCOUNT.\n\nConsider yourself bothered.",
        ".$ACCOUNT, what time is it in your time zone? It's $TIME here.",
        ".$ACCOUNT exists... weird flex but okay.",
        ".$ACCOUNT, good luck next season...",
        "You should drop a follow... $ACCOUNT"
)

fun getRandomFlavorText(team: TeamInfo): String {
    val rawText = flavorTexts[(Math.random() * flavorTexts.size).toInt()]

    return rawText
            .replace(ACCOUNT, "@${team.twitter}")
            .replace(TIME, "${team.hour}:${team.minute}${if (team.second != 0) ":${team.second}" else ""}")
}