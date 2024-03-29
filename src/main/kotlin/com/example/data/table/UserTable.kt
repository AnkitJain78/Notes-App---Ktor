package com.example.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val email = varchar("email", 512)
    val hashPass = varchar("hashPass", 512)
    val userName = varchar("userName", 512)
    override val primaryKey: PrimaryKey? = PrimaryKey(email)
}