import me.greggkr.frctwitterbot.config
import me.greggkr.frctwitterbot.util.Config
import twitter4j.StatusUpdate
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val twitterConfig = ConfigurationBuilder()
        .setOAuthConsumerKey(config[Config.Twitter.consumerkey])
        .setOAuthConsumerSecret(config[Config.Twitter.consumersecret])
        .setOAuthAccessToken(config[Config.Twitter.accesstoken])
        .setOAuthAccessTokenSecret(config[Config.Twitter.accesstokensecret])
        .build()

private val twitter = TwitterFactory(twitterConfig).instance

private val scheduler = Executors.newSingleThreadScheduledExecutor()

fun main(args: Array<String>) {
    scheduler.scheduleAtFixedRate({
        val status = StatusUpdate("test")
        status.setMedia(File("header.png"))
        twitter.updateStatus(status)
    }, 5, 5, TimeUnit.SECONDS)
}