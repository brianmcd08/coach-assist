package io.vonteegoway.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.vonteegoway.routes.*

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            teams()
            players()
            teamAssignments()
            positions()
        }
    }
}
