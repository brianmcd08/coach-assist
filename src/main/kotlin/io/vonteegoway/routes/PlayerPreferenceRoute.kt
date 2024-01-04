package io.vonteegoway.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.vonteegoway.dao.playerPreference.*
import io.vonteegoway.dao.team.*
import io.vonteegoway.models.PlayerPreference
import io.vonteegoway.dao.player.*
import io.vonteegoway.dao.position.*


fun Route.playerPreferences() {
    get("playerPreferences") {
        val allPreferences = playerPreferenceDao.allPlayerPreferences()
        call.respond(allPreferences)
    }

    get("playerPreferences/team/{id}") {
        val teamId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val preferences = playerPreferenceDao.playerPreferencesByTeam(teamId)
        call.respond(preferences)
    }

    get("playerPreferences/player/{id}") {
        val playerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val preferences = playerPreferenceDao.playerPreferencesByPlayer(playerId)
        call.respond(preferences)
    }

//    Need to validate that playerId, teamId, and position exist
    put("playerPreference") {
        val preferenceInfo = call.receive<PlayerPreference>()
        val playerId = preferenceInfo.playerId
        if (playerId !in playerDao.allPlayers().map { it.id }) throw NotFoundException()
        val teamId = preferenceInfo.teamId
        if (teamId !in teamDao.allTeams().map { it.id }) throw NotFoundException()
        val position = preferenceInfo.position
        if (position !in positionDao.allPositions().map { it.id }) throw NotFoundException()
        val preference = preferenceInfo.preference
        playerPreferenceDao.upsertPreference(playerId, teamId, position, preference)
        call.respond(HttpStatusCode.Accepted)
    }

//    Need to add proper responses; not just assuming all is ok if we get to the final line in the method
    delete("playerPreference/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        playerPreferenceDao.removePreference(id)
        call.respond(HttpStatusCode.OK)
    }
}