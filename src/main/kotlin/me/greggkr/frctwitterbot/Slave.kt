package me.greggkr.frctwitterbot

import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import twitter4j.StatusUpdate
import twitter4j.Twitter
import java.io.File
import java.net.URL
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * The idea of this class is to have it represent a single Twitter account so that we do not have to deal with the tweet limit.
 */
class Slave(private val twitter: Twitter) : Bot {
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    override fun tweet(team: Pair<Int, TeamInfo>) {
        val status = StatusUpdate(getRandomFlavorText(team.second))
        val image = getRandomImage(team)

        if (image == null) {
            LoggerFactory.getLogger(javaClass).debug("Failed to get image for ${team.first}")
        } else {
            status.setMedia(image)

            scheduler.schedule({
                image.delete()
            }, 3, TimeUnit.MINUTES)
        }

        val update = twitter.updateStatus(status)
        twitter.createFavorite(update.id)
    }

    // TODO: Move into util
    private fun getRandomImage(team: Pair<Int, TeamInfo>): File? {
        val images = team.second.images
        if (images == null || images.isEmpty()) return null

        val image = images[Random().nextInt(images.size)]
        val file = File("img/twitter/${team.first}-${UUID.randomUUID()}.jpg")

        FileUtils.copyURLToFile(URL(image), file)
        return file
    }

    // TODO: Move into util
    private fun getDelay(hour: Int, min: Int, sec: Int = 0, timezone: String): Long {
        val zone = try {
            ZoneId.of(timezone)
        } catch (e: Exception) {
            LoggerFactory.getLogger(javaClass).debug("Failed to get timezone '$timezone'")
            ZoneId.of("America/Chicago")
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