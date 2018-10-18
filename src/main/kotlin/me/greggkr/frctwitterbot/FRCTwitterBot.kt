package me.greggkr.frctwitterbot

import com.natpryce.konfig.ConfigurationProperties
import me.greggkr.frctwitterbot.util.*
import okhttp3.OkHttpClient
import okhttp3.Request
import twitter4j.StatusUpdate
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import java.io.File
import java.io.FileOutputStream
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/*

Timezone constants.
Format: GMT_M/P_HOUR_MIN

Examples: GMT_M_6_00 = GMT-6:00
          GMT_P_6_00 = GMT+6:00


 */


private val cacheFolder = File("img_cache/")

val config = ConfigurationProperties.fromFile(File("config.properties"))

class FRCTwitterBot {
    private val twitterConfig = ConfigurationBuilder()
            .setOAuthConsumerKey(config[Config.Twitter.consumerkey])
            .setOAuthConsumerSecret(config[Config.Twitter.consumersecret])
            .setOAuthAccessToken(config[Config.Twitter.accesstoken])
            .setOAuthAccessTokenSecret(config[Config.Twitter.accesstokensecret])
            .build()

    private val twitter = TwitterFactory(twitterConfig).instance
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(it.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Dillo/2.0")
                        .build())
            }
            .build()

    fun start() {
        teams.forEach {
            val info = it.value

            scheduler.scheduleAtFixedRate({
                val team = it.key
                val status = StatusUpdate(getRandomFlavorText(info))

                val image = getRandomImage(Pair(team, info))

                if (image == null) {
                    println("Failed to get image for team $team")
                } else {
                    status.setMedia(image)

                    scheduler.schedule({
                        image.delete()
                    }, 1, TimeUnit.MINUTES)
                }

                val update = twitter.updateStatus(status)
                twitter.createFavorite(update.id)
                println("Tweeted for $team")
            }, getDelay(info.hour, info.minute, info.second, info.timezone), 12 * 60 * 60, TimeUnit.SECONDS)
        }
        println("Scheduled")
    }

    private fun getRandomImage(team: Pair<Int, TeamInfo>): File? {
        try {
            val images = team.second.images ?: return null
            if (images.isEmpty()) return null

            val image = images[Random().nextInt(images.size)]
            val file = File(cacheFolder, "${team.first}-${UUID.randomUUID()}.jpg")

            val req = Request.Builder()
                    .url(image)
                    .build()

            val res = httpClient.newCall(req).execute()

            val fos = FileOutputStream(file)
            fos.write(res.body()?.bytes() ?: return null)
            fos.close()

            return file
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
    }

    private fun getDelay(hour: Int, min: Int, sec: Int, timezone: String): Long {
        val zone = try {
            ZoneId.of(timezone)
        } catch (e: Exception) {
            println("Failed to get timezone '$timezone'")
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
    cacheFolder.mkdirs()
    setupTeams()
    FRCTwitterBot().start()
}