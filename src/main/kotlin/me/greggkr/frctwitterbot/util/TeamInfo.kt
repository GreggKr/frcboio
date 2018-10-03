package me.greggkr.frctwitterbot.util

import java.util.*

data class TeamInfo(val twitter: String,
                    val hour: Int,
                    val minute: Int,
                    val second: Int = 0,
                    val timezone: String = "America/Chicago",
                    val images: Array<String>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamInfo

        if (twitter != other.twitter) return false
        if (hour != other.hour) return false
        if (minute != other.minute) return false
        if (second != other.second) return false
        if (timezone != other.timezone) return false
        if (!Arrays.deepEquals(images, other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = twitter.hashCode()
        result = 31 * result + hour
        result = 31 * result + minute
        result = 31 * result + second
        result = 31 * result + timezone.hashCode()
        result = 31 * result + (images?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}