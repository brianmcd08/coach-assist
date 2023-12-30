package io.vonteegoway.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.vonteegoway.models.Player
import io.vonteegoway.dao.player.playerDao


fun Route.players() {
    get("players") {
        val allPlayers = playerDao.allPlayers()
        call.respond(allPlayers)
    }

    get("player/{id}") {
        val playerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val player = playerDao.player(playerId) ?: throw NotFoundException()
        call.respond(player)
    }

    put("player/{id}") {
        val playerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        val player = call.receive<Player>()
        playerDao.editPlayer(playerId, player.firstName, player.lastName, player.playerNum)
        call.respond(HttpStatusCode.Accepted)
    }

    post("player") {
        //val player = call.receive<Player>()
        val player = call.receive<Player>()

        playerDao.addNewPlayer(player.firstName, player.lastName, player.playerNum)
        call.respond(HttpStatusCode.Accepted)
    }

    delete("player/{id}") {
        val playerId = call.parameters["id"]?.toIntOrNull() ?: throw NotFoundException()
        playerDao.deletePlayer(playerId)
        call.respond(HttpStatusCode.OK)
    }
}