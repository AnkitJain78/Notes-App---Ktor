package com.example.routes

import com.example.data.dao.NoteDao
import com.example.data.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val NOTES = "$API_VERSION/notes"
const val CREATE_NOTES = "$NOTES/create"
const val UPDATE_NOTES = "$NOTES/update"
const val DELETE_NOTES = "$NOTES/delete"

@Location(CREATE_NOTES)
class NoteCreateRoute

@Location(NOTES)
class NoteGetRoute

@Location(UPDATE_NOTES)
class NoteUpdateRoute

@Location(DELETE_NOTES)
class NoteDeleteRoute


fun Route.NoteRoutes(
    dao: NoteDao
) {
    authenticate("jwt") {

        post<NoteCreateRoute> {

            call.application.environment.log.trace("this is an note input from user \n")

            val note = try {
                call.receive<Note>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields"))
                return@post
            }

            try {
                val email = call.principal<User>()!!.email
                dao.addNote(note, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note Added Successfully!"))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problem Occurred!"))
            }
        }

        get<NoteGetRoute> {

            try {
                val email = call.principal<User>()!!.email
                val notes = dao.getAllNotes(email)
                call.respond(HttpStatusCode.OK, notes.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, emptyList<Note>())
            }
        }


        post<NoteUpdateRoute> {

            val note = try {
                call.receive<Note>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields"))
                return@post
            }

            try {

                val email = call.principal<User>()!!.email
                dao.updateNote(note, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note Updated Successfully!"))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problem Occurred!"))
            }

        }


        delete<NoteDeleteRoute> {

            val noteId = try {
                call.request.queryParameters["id"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:id is not present"))
                return@delete
            }


            try {
                val email = call.principal<User>()!!.email
                dao.deleteNote(noteId, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note Deleted Successfully!"))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some problem Occurred!"))
            }
        }
    }
}
