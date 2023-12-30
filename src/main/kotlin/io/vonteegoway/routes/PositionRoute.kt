package io.vonteegoway.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.vonteegoway.dao.position.positionDao


fun Route.positions() {
    get("positions") {
        val allPositions = positionDao.allPositions()
        call.respond(allPositions)
    }
}