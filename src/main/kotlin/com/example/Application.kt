package com.example

import com.example.authentication.JwtService
import com.example.data.dao.DatabaseFactory
import com.example.data.dao.NoteDao
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
fun Application.module() {
    DatabaseFactory.init()
    val jwtService by inject<JwtService>()
    val dao by inject<NoteDao>()
    configureLocations()
    configureSerialization()
    configureKoin()
    configureSecurity(dao, jwtService)
    configureRouting(dao, jwtService)
}
