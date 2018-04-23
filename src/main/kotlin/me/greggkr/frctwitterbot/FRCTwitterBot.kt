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
    private val config = ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey("eDAOA2vjBl3CmbvtWRhMJaAE3")
            .setOAuthConsumerSecret("1aLTLGsV6ejaaxWba8akZIsMIbn2lrW3D590RiQKbPwWzFjKUg")
            .setOAuthAccessToken("987904348947730432-y1JKFM2mJpwNeaZwWh4oWL4raPU6wXE")
            .setOAuthAccessTokenSecret("evsIAPwNH80EOzXJDsw0iA3zQ9s2cvu5XDaA7Jw1M8D7O")
            .build()
    private val twitter = TwitterFactory(config).instance

    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    fun run() {
        println("starting tweeting service")
                
        scheduler.scheduleAtFixedRate({
            println(twitter.updateStatus("@Robonauts118 It is 1:18."))
        }, getDelay(1, 18), 12 * 60 * 60, TimeUnit.SECONDS)

        scheduler.scheduleAtFixedRate({
            println(twitter.updateStatus("@team254 It is 2:54."))
        }, getDelay(2, 54), 12 * 60 * 60, TimeUnit.SECONDS)
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