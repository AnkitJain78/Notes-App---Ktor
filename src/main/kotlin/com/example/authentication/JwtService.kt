package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.model.User

class JwtService {
    private val issuer = "noteServer"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algo = Algorithm.HMAC256(jwtSecret)

    val verifier = JWT
        .require(algo)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: User): String = JWT
            .create()
            .withIssuer(issuer)
            .withSubject("Note Authentication")
            .withClaim("email", user.email)
            .sign(algo)
}