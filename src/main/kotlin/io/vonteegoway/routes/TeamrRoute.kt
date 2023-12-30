package io.vonteegoway.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.vonteegoway.models.Team
import io.vonteegoway.dao.team.teamDao


fun Route.teams() {
    get("teams") {
        val allTeams = teamDao.allTeams()
        call.respond(allTeams)
    }

    get("team/{id}") {
        val teamId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val team = teamDao.team(teamId) ?: throw NotFoundException()
        call.respond(team)
    }

    put("team/{id}") {
        val teamId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val teamName = call.receive<Team>().teamName
        teamDao.editTeam(teamId, teamName)
        call.respond(HttpStatusCode.Accepted)
    }

    post("team") {
        val teamName = call.receive<Team>().teamName
        teamDao.addNewTeam(teamName)
        call.respond(HttpStatusCode.Accepted)
    }

    delete("team/{id}") {
        val teamId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        teamDao.deleteTeam(teamId)
        call.respond(HttpStatusCode.OK)
    }
}