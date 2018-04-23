package me.greggkr.frctwitterbot

import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class FRCTwitterBot {
    private val twitterConfig = ConfigurationBuilder()
            .setOAuthConsumerKey(CONSUMER_KEY)
            .setOAuthConsumerSecret(CONSUMER_SECRET)
            .setOAuthAccessToken(ACCESS_TOKEN)
            .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET)
            .build()

    private val twitter = TwitterFactory(twitterConfig).instance
    private val scheduler = Executors.newSingleThreadScheduledExecutor()
    private val teams = HashMap<Int, TeamInfo>()

    init {
        teams[118] = TeamInfo("Robonauts118", 1, 18)
        teams[254] = TeamInfo("team254", 2, 54)
    }

    fun run() {
        println("starting tweeting service")

        for (team in teams) {
            val info = team.value

            val hour = info.hour
            val min = info.minute
            scheduler.scheduleAtFixedRate({
                twitter.updateStatus("@${info.twitter}, it is $hour:$min")
                println("[${team.key}] updated status")
            }, getDelay(hour, min), 12 * 60 * 60, TimeUnit.SECONDS)
        }
    }

    private fun getDelay(hour: Int, min: Int, sec: Int = 0): Long {
        val now = LocalDateTime.now()
        val zone = ZoneId.of("America/Chicago")
        val zonedNow = ZonedDateTime.of(now, zone)
        var next = zonedNow
                .withHour(hour)
                .withMinute(min)
                .withSecond(sec)

        if (zonedNow > next) next = next.plusDays(1)

        val dir = Duration.between(zonedNow, next)

        return dir.seconds
    }
}

fun Array<String>.main() {
    FRCTwitterBot().run()
}