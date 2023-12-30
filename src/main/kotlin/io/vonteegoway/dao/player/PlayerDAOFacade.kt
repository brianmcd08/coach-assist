package io.vonteegoway.dao.player

import io.vonteegoway.models.*

interface PlayerDAOFacade {
    suspend fun allPlayers(): List<Player>
    suspend fun player(id: Int): Player?
    suspend fun addNewPlayer(firstName: String, lastName: String, playerNum: Int): Player?
    suspend fun editPlayer(id: Int, firstName: String, lastName: String, playerNum: Int): Boolean
    suspend fun deletePlayer(id: Int): Boolean
}
