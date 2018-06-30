package me.greggkr.frctwitterbot

import com.google.common.base.Stopwatch
import com.natpryce.konfig.ConfigurationProperties
import me.greggkr.frctwitterbot.handler.DMHandler
import me.greggkr.frctwitterbot.util.Config
import me.greggkr.frctwitterbot.util.TeamInfo
import me.greggkr.frctwitterbot.util.getRandomFlavorText
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import twitter4j.StatusUpdate
import twitter4j.Twitter
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
private const val GMT_M_8_00 = "US/Pacific"
private const val GMT_M_6_00 = "America/Chicago"
private const val GMT_M_5_00 = "America/Indianapolis"
private const val GMT_M_4_00 = "US/Michigan"
private const val GMT_M_10_00 = "Pacific/Honolulu"

val config = ConfigurationProperties.fromFile(File("config.properties"))

class FRCTwitterBot {
    private val logger = LoggerFactory.getLogger(FRCTwitterBot::class.java)

    private val twitterConfig = ConfigurationBuilder()
            .setOAuthConsumerKey(config[Config.Twitter.consumerkey])
            .setOAuthConsumerSecret(config[Config.Twitter.consumersecret])
            .setOAuthAccessToken(config[Config.Twitter.accesstoken])
            .setOAuthAccessTokenSecret(config[Config.Twitter.accesstokensecret])
            .build()

    private val twitter = TwitterFactory(twitterConfig).instance
    private val scheduler = Executors.newSingleThreadScheduledExecutor()
    private val teams = HashMap<Int, TeamInfo>()

    private val dmHandler = DMHandler(twitter, config[Config.Bot.owner])

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(it.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Dillo/2.0")
                        .build())
            }
            .build()

    init {
        val stopwatch = Stopwatch.createStarted()
        teams[111] = TeamInfo("111wildstang", 1, 11, images = arrayOf(
                "https://i.ytimg.com/vi/ko9g-hTs4VE/maxresdefault.jpg",
                "https://www.chiefdelphi.com/media/img/9eb/9eb5e39ea29029df55dd709a83168f06_l.jpg",
                "https://frctracker.com/team_photos/111.jpg",
                "http://wildstang.org/wp-content/uploads/2016/03/MG_2969-1024x683.jpg",
                "http://wildstang.org/wp-content/uploads/2016/03/MG_2974-1024x683.jpg"
        ))
        teams[118] = TeamInfo("Robonauts118", 1, 18, images = arrayOf(
                "https://i.ytimg.com/vi/RQNCeHsOeJE/maxresdefault.jpg",
                "https://i.ytimg.com/vi/0fRt6sdKN7Y/maxresdefault.jpg",
                "http://www.chiefdelphi.com/media/img/757/7571702de56d63786d6ce013dc2b108b_l.jpg",
                "https://i.ytimg.com/vi/jt6Jq439WQc/mqdefault.jpg",
                "https://www.chiefdelphi.com/media/img/11a/11a5aa1b4adc1074160810ae83897598_m.jpg"
        ))
        teams[148] = TeamInfo("Robowranglers", 1, 48, images = arrayOf(
                "https://i.imgur.com/gZ8QMAMh.jpg",
                "https://farm4.static.flickr.com/3420/3360265311_a634f47bda_b.jpg",
                "https://c1.staticflickr.com/4/3065/2493139117_08ef7eeef3_b.jpg",
                "https://i.ytimg.com/vi/1QOYdA5IPJQ/maxresdefault.jpg",
                "http://2014.robowranglers148.com/uploads/1/0/5/4/10542658/__2038677.jpg"
        ))
        teams[217] = TeamInfo("tc_217", 2, 17, GMT_M_4_00, arrayOf(
                "https://i.pinimg.com/originals/32/f3/9b/32f39bfba9e70679f37d3a36c470a195.jpg",
                "https://i.pinimg.com/originals/46/89/1e/46891ef233a35b0c331b7d0606f7c3e9.jpg",
                "https://i.pinimg.com/originals/ee/4c/e5/ee4ce5e87011b4d59f5789d1bf20bd9a.jpg",
                "https://i.pinimg.com/originals/f2/9a/7a/f29a7a8c1691f5e5ae74b987f9fb4de7.jpg",
                "http://farm4.static.flickr.com/3120/2493098131_1edd8eec91.jpg"
        ))
        teams[254] = TeamInfo("team254", 2, 54, GMT_M_8_00, arrayOf(
                "https://i.imgur.com/oRcMKDA.jpg",
                "https://i.imgur.com/dynbBAM.jpg",
                "https://i.imgur.com/lqN0duh.jpg",
                "https://i.imgur.com/Gi8Jhyj.jpg",
                "https://i.imgur.com/5dSNA7S.jpg"
        ))
        teams[330] = TeamInfo("330_beachbots", 3, 30, GMT_M_8_00, arrayOf(
                "https://i.ytimg.com/vi/irP_K4utKgQ/maxresdefault.jpg",
                "https://i.imgur.com/QTVEqKXh.jpg",
                "https://www.chiefdelphi.com/media/img/cbe/cbe689388b850647a922111b05e54dc2_m.jpg",
                "https://farm3.staticflickr.com/2348/2494025028_4166001c90_b.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEZ6kyKARI1D4hj99CPvlOzDLqMhPyvN8Lrbh9crvmM4FTFxBC"
        ))
        teams[359] = TeamInfo("thehawaiiankids", 3, 59, GMT_M_10_00, arrayOf(
                "https://www.chiefdelphi.com/media/img/b59/b5942cbf1bb652f705936fc4e1413c26_m.jpg",
                "https://www.chiefdelphi.com/media/img/410/41098453587ce6947556713644626cc0_l.jpg",
                "https://i.ytimg.com/vi/YGt6YtOB5Ow/hqdefault.jpg",
                "https://pbs.twimg.com/media/DWietdQX4AAB6yN.jpg",
                "http://www.waialuarobotics.com/2007-2008/images/hawaii/defense.jpg"
        ))
        teams[441] = TeamInfo("fake441", 4, 41, images = arrayOf(
                "https://i.ytimg.com/vi/D-UxSqM9kW8/hqdefault.jpg",
                "https://i.ytimg.com/vi/OA7dbcxgesk/hqdefault.jpg"
        ))
        teams[449] = TeamInfo("FRCteam449", 4, 49, GMT_M_5_00, arrayOf(
                "https://i.imgur.com/zXwTpD7.jpg",
                "https://i.imgur.com/4QsOLhR.jpg",
                "https://i.imgur.com/LkxxOLN.jpg",
                "https://i.imgur.com/0edJv5j.jpg",
                "https://i.imgur.com/ql37fL9.jpg"
        ))
        teams[624] = TeamInfo("frc624", 6, 24, images = arrayOf(
                "http://team624.org/images/slideshow/BrimstoneSlide.jpg",
                "http://dev.team624.org/images/slideshow/offseason-slide.jpg",
                "https://www.chiefdelphi.com/media/img/c39/c39ebf0cd9b5f6a8a56a728ef503308c_l.jpg",
                "https://s.hdnux.com/photos/53/00/04/11272461/5/920x920.jpg"
        ))
        teams[847] = TeamInfo("PHRED847", 8, 47, GMT_M_8_00, arrayOf(
                "https://i.imgur.com/81xG0SK.jpg",
                "https://i.imgur.com/oC3gvYW.jpg",
                "https://i.imgur.com/6VYk2FN.jpg",
                "https://i.imgur.com/T066hf9.jpg"
        ))
        teams[1011] = TeamInfo("crush1011", 10, 11, images = arrayOf(
                "https://d2g8igdw686xgo.cloudfront.net/19890370_1493076853.2214.jpg",
                "https://i.imgur.com/0s4BIlMh.jpg",
                "https://i.imgur.com/OdWHW6Y.jpg",
                "https://i.imgur.com/A6zMZjz.jpg",
                "https://www.nau.edu/uploadedImages/Homepage/Landing/EMSA/robot_carry720.jpg"
        ))
        teams[1114] = TeamInfo("frc1114", 11, 14, GMT_M_4_00, arrayOf(
                "http://www.simbotics.org/files/photos/grtw2012-triple.jpg",
                "https://i.pinimg.com/originals/2d/3d/a5/2d3da5a0eaf378b636edc84faf367e8d.jpg",
                "https://pbs.twimg.com/media/Bh6rpmtCMAArr3a.jpg",
                "https://i.pinimg.com/474x/8f/a6/4c/8fa64c84650d118258ea7cf748f3d58b--robotics-fame.jpg",
                "https://i.imgur.com/tLyD2Vbh.jpg"
        ))

        stopwatch.stop()
        logger.info("Created teams in ${stopwatch}")
    }

    fun start() {
        logger.info("Setting up schedulers...")

        val stopwatch = Stopwatch.createStarted()
        teams.forEach {
            val info = it.value

            val hour = info.hour
            val min = info.minute
            scheduler.scheduleAtFixedRate({
                val team = it.key

                val teamStopwatch = Stopwatch.createUnstarted()

                val status = StatusUpdate(getRandomFlavorText(info))

                teamStopwatch.start()
                val image = getRandomImage(Pair(team, info))
                teamStopwatch.stop()
                logger.info("Got random image in $teamStopwatch")

                if (image == null) {
                    println("Failed to get image for team $team")
                } else {
                    status.setMedia(image)

                    // Possible fix to stopping of reposting of images?
                    scheduler.schedule({
                        image.delete()
                    }, 1, TimeUnit.MINUTES)
                }


                teamStopwatch
                        .reset()
                        .start()
                val update = twitter.updateStatus(status)
                teamStopwatch.stop()
                logger.info("Updated status for $team in $teamStopwatch")

                teamStopwatch
                        .reset()
                        .start()
                twitter.createFavorite(update.id)
                logger.info("Liked status for $team in $teamStopwatch")
                teamStopwatch.stop()

                logger.info("Sent Tweet for $team")
            }, getDelay(hour, min, timezone = info.timezone), 12 * 60 * 60, TimeUnit.SECONDS)
        }
        logger.info("Finished setting up schedulers in $stopwatch")

        stopwatch.reset()
        stopwatch.start()
        dmHandler.start()
        logger.info("Started DM handler in $stopwatch")
        stopwatch.stop()
    }

    private fun getRandomImage(team: Pair<Int, TeamInfo>): File? {
        val images = team.second.images ?: return null
        if (images.isEmpty()) return null

        val image = images[Random().nextInt(images.size)]
        val file = File("img/twitter/${team.first}-${UUID.randomUUID()}.jpg")

        val req = Request.Builder()
                .url(image)
                .build()

        val res = httpClient.newCall(req).execute()

        val fos = FileOutputStream(file)
        fos.write(res.body()?.bytes() ?: return null)
        fos.close()

        return file
    }

    private fun getDelay(hour: Int, min: Int, sec: Int = 0, timezone: String): Long {
        val zone = try {
            ZoneId.of(timezone)
        } catch (e: Exception) {
            logger.info("Failed to get timezone '$timezone'")
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

fun Twitter.dmBotOwner(msg: String) {
    this.sendDirectMessage(config[Config.Bot.owner], msg)
}

fun Array<String>.main() {
    FRCTwitterBot().start()
}