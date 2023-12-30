package io.vonteegoway.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.vonteegoway.dao.teamAssignment.*
import io.vonteegoway.models.TeamAssignment
import javax.management.InvalidAttributeValueException


fun Route.teamAssignments() {
    get("teamAssignments") {
        val allTeamAssignments = teamAssignmentDao.allTeamAssignments()
        call.respond(allTeamAssignments)
    }

    get("teamAssignment/team/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val teamAssignment = teamAssignmentDao.teamAssignmentsByTeam(id) ?: throw NotFoundException()
        call.respond(teamAssignment)
    }

    get("teamAssignment/player/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val teamAssignment = teamAssignmentDao.teamAssignmentsByPlayer(id) ?: throw NotFoundException()
        call.respond(teamAssignment)
    }

    post("teamAssignment") {
        val teamAssignment = call.receive<TeamAssignment>()
        teamAssignmentDao.addNewTeamAssignment(teamAssignment.teamId, teamAssignment.playerId) ?: throw InvalidAttributeValueException()
        call.respond(HttpStatusCode.Accepted)
    }

    delete("teamAssignment/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        teamAssignmentDao.deleteTeamAssignment(id)
        call.respond(HttpStatusCode.OK)
    }

    delete("teamAssignment/player/{id}") {
        val playerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        teamAssignmentDao.deleteTeamAssignmentsByPlayer(playerId)
        call.respond(HttpStatusCode.OK)
    }

    delete("teamAssignment/team/{id}") {
        val teamId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        teamAssignmentDao.deleteTeamAssignmentsByTeam(teamId)
        call.respond(HttpStatusCode.OK)
    }

}