package com.example.data.dao

import com.example.data.dao.DatabaseFactory.dbQuery
import com.example.data.model.Note
import com.example.data.model.User
import com.example.data.table.NoteTable
import com.example.data.table.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NoteDaoImpl: NoteDao {

    override suspend fun addNote(note: Note, email: String) {
        dbQuery{
            NoteTable.insert {
                it[NoteTable.email] = email
                it[NoteTable.id] = note.id
                it[NoteTable.title] = note.title
                it[NoteTable.description] = note.description
                it[NoteTable.date] = note.date
            }
        }
    }

    override suspend fun findUserByEmail(email: String): User? =
        dbQuery{
                UserTable.select { UserTable.email.eq(email)}
                    .map {
                        resultRowToUser(it)
                    }
                    .singleOrNull()
        }

    override suspend fun getAllNotes(email: String): List<Note>? =
         dbQuery{
            NoteTable.select {
                NoteTable.email.eq(email)
            }
                .mapNotNull { resultRowToNote(it) }
        }

    override suspend fun updateNote(note: Note, email: String): Boolean =
        dbQuery {
            NoteTable.update({NoteTable.id.eq(note.id) and NoteTable.email.eq(email)}){
                it[NoteTable.title] = note.title
                it[NoteTable.description] = note.description
                it[NoteTable.date] = note.date
            }
        } > 0

    override suspend fun deleteNote(id:String,email: String): Boolean =
        dbQuery {
            NoteTable.deleteWhere { NoteTable.email.eq(email) and NoteTable.id.eq(id) }
        } > 0

    override suspend fun addUser(user: User) {
        dbQuery{
            UserTable.insert {
                it[UserTable.email] = user.email
                it[UserTable.hashPass] = user.hashPass
                it[UserTable.userName] = user.userName
            }
        }
    }

    private fun resultRowToNote(row: ResultRow): Note?{
        if(row == null){
            return null
        }
        return Note(
            id = row[NoteTable.id],
            title = row[NoteTable.title],
            description = row[NoteTable.description],
            date = row[NoteTable.date]
        )
    }

    private fun resultRowToUser(row: ResultRow): User?{
        if(row == null){
            return null
        }
        return User(
            email = row[UserTable.email],
            hashPass = row[UserTable.hashPass],
            userName = row[UserTable.userName]
        )
    }
}