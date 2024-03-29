package com.example.data.dao

import com.example.data.model.Note
import com.example.data.model.User

interface NoteDao {
    suspend fun addNote(note: Note, email: String)
    suspend fun findUserByEmail(email: String): User?
    suspend fun getAllNotes(email: String): List<Note>?
    suspend fun updateNote(note: Note, email: String): Boolean
    suspend fun addUser(user: User)
    suspend fun deleteNote(id: String, email: String): Boolean
}