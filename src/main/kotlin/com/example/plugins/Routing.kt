package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.data.dao.NoteDao
import com.example.data.model.User
import com.example.routes.NoteRoutes
import com.example.routes.UserRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting(dao: NoteDao, jwtService: JwtService) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        NoteRoutes(dao)
        UserRoutes(dao, jwtService)
    }
}
