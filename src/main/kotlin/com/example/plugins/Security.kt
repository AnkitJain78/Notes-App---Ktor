package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.example.authentication.JwtService
import com.example.data.dao.NoteDao
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSecurity(dao: NoteDao, jwtService: JwtService) {

    // Please read the jwt property from the config file if you are using EngineMain
    authentication {
        jwt("jwt") {
            realm = "Note Server"
            verifier(jwtService.verifier)
            validate { credential ->
                val payload = credential.payload
                val email: String = payload.getClaim("email").asString()
                val user = dao.findUserByEmail(email)
                user
            }
        }
    }
    data class MySession(val count: Int = 0)
}
