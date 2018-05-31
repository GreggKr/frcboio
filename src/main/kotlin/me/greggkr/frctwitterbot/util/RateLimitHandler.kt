package me.greggkr.frctwitterbot.util

import twitter4j.RateLimitStatusEvent
import twitter4j.RateLimitStatusListener
import twitter4j.Twitter

class RateLimitHandler(private val twitter: Twitter) : RateLimitStatusListener {
    override fun onRateLimitStatus(event: RateLimitStatusEvent) {
        twitter.sendDirectMessage(OWNER, "RL Status Recieved:\n" +
                "Account Rate limited: ${event.isAccountRateLimitStatus}\n" +
                "Tweets Remaining: ${event.rateLimitStatus.remaining}")
    }

    override fun onRateLimitReached(event: RateLimitStatusEvent) {
        twitter.sendDirectMessage(OWNER, "RLed.\n" +
                "Reset In: ${event.rateLimitStatus.resetTimeInSeconds}")
    }
}