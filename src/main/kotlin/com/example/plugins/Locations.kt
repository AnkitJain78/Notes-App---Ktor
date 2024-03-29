package com.example.plugins

import com.example.di.appModule
import io.ktor.server.application.*
import io.ktor.server.locations.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureLocations() {
    install(Locations) {
    }
}