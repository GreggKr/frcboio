package me.greggkr.frctwitterbot.util

import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType

class Config {
    object Twitter : PropertyGroup() {
        val consumerkey by stringType
        val consumersecret by stringType

        val accesstoken by stringType
        val accesstokensecret by stringType
    }
}