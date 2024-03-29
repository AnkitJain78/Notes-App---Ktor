package com.example.data.table

import org.jetbrains.exposed.sql.Table

object NoteTable: Table() {
    val id = varchar("id", 512)
    val title = text("title")
    val description = text("description")
    val email = varchar("email",512).references(UserTable.email)
    val date = long("date")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}