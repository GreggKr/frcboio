package me.greggkr.frctwitterbot

import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/*

Timezone constants.
Format: GMT_M/_HOUR_MIN

Example: GMT_M_6_00 = GMT-6:00


 */
private const val GMT_M_8_00 = "US/Pacific"
private const val GMT_M_6_00 = "America/Chicago"
private const val GMT_M_4_00 = "US/Michigan"
private const val GMT_M_10_00 = "Pacific/Honolulu"

class FRCTwitterBot {
    private val twitterConfig = ConfigurationBuilder()
            .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
            .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
            .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
            .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET)
            .build()

    private val twitter = TwitterFactory(twitterConfig).instance
    private val scheduler = Executors.newSingleThreadScheduledExecutor()
    private val teams = HashMap<Int, TeamInfo>()

    init {
        teams[111] = TeamInfo("111wildstang", 1, 11)
        teams[118] = TeamInfo("Robonauts118", 1, 18)
        teams[148] = TeamInfo("Robowranglers", 1, 48)
        teams[217] = TeamInfo("tc_217", 2, 17, GMT_M_4_00)
        teams[254] = TeamInfo("team254", 2, 54, GMT_M_8_00)
        teams[330] = TeamInfo("330_beachbots", 3, 30, GMT_M_8_00)
        teams[359] = TeamInfo("thehawaiiankids", 3, 59, GMT_M_10_00)
        teams[624] = TeamInfo("frc624", 6, 24)
        teams[1114] = TeamInfo("frc1114", 11, 14, GMT_M_4_00)
    }

    fun run() {
        println("starting tweeting service")

        teams.forEach {
            val info = it.value

            val hour = info.hour
            val min = info.minute
            scheduler.scheduleAtFixedRate({
                twitter.updateStatus(getRandomFlavorText(info))
                println("[${it.key}] updated status")
            }, getDelay(hour, min, timezone = info.timezone), 12 * 60 * 60, TimeUnit.SECONDS)
        }
    }

    private fun getDelay(hour: Int, min: Int, sec: Int = 0, timezone: String): Long {
        val zone = try {
            ZoneId.of(timezone)
        } catch (e: Exception) {
            println("Failed for timezone of $timezone")
            ZoneId.of(GMT_M_6_00)
        }


        val now = LocalDateTime.now(zone)

        var next = now
                .withHour(hour)
                .withMinute(min)
                .withSecond(sec)

        if (now > next) next = next.plusDays(1)

        val dir = Duration.between(now, next)
        return dir.seconds
    }
}

fun Array<String>.main() {
    FRCTwitterBot().run()
}