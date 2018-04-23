package me.greggkr.frctwitterbot

import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType

class Config {
    object Twitter : PropertyGroup() {
        object Consumer : PropertyGroup() {
            val key by stringType
            val secret by stringType
        }

        object Access : PropertyGroup() {
            val token by stringType
            val secret by stringType
        }
    }
}