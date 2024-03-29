package com.example.data.model

import io.ktor.server.auth.*

data class User(val email: String,
    val hashPass: String,
    val userName: String) : Principal
