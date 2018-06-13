package me.greggkr.frctwitterbot.handler

import com.google.gson.GsonBuilder
import me.greggkr.frctwitterbot.util.HttpUtil
import me.greggkr.frctwitterbot.util.OWNER
import org.json.JSONObject
import twitter4j.DirectMessage
import twitter4j.ResponseList
import twitter4j.Twitter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private const val STATS_DUMP = "stats"

// TODO: Use Paging
class DMHandler(private val twitter: Twitter) {
    private lateinit var lastDMs: ResponseList<DirectMessage>

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val executor = Executors.newSingleThreadScheduledExecutor()

    fun start() {
        println("Started listening for DMs")

        executor.scheduleAtFixedRate({
            try {
                val dms = twitter.directMessages().directMessages

                if (::lastDMs.isInitialized && lastDMs != dms) {
                    val new = dms.filter { !lastDMs.contains(it) }

                    for (dm in new) {
                        println(dm.sender.screenName)
                        if (dm.sender.screenName == OWNER) {
                            val text = dm.text

                            if (text.toLowerCase().contains(STATS_DUMP)) {
                                val json = JSONObject()
                                        .put("OS", "test")

                                val prettyJson = HttpUtil.hastebin(gson.toJson(json))

                                twitter.sendDirectMessage(OWNER, "FRC Boio statistics: $prettyJson")
                            }
                        }
                    }
                }

                lastDMs = dms
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 30, 30, TimeUnit.SECONDS)
    }
}