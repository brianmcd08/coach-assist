package io.vonteegoway.dao.player

import io.vonteegoway.dao.DatabaseFactory.dbQuery
import io.vonteegoway.models.Player
import io.vonteegoway.models.Players
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PlayerDAOFacadeImpl : PlayerDAOFacade {
    private fun resultRowToPlayer(row: ResultRow) = Player(
        id = row[Players.id],
        firstName = row[Players.firstName],
        lastName = row[Players.lastName],
        playerNum = row[Players.playerNum]
    )

    override suspend fun allPlayers(): List<Player> = dbQuery {
        Players.selectAll().map(::resultRowToPlayer)
    }

    override suspend fun player(id: Int): Player? = dbQuery {
        Players
            .select { Players.id eq id }
            .map(::resultRowToPlayer)
            .singleOrNull()
    }

    override suspend fun addNewPlayer(firstName: String, lastName: String, playerNum: Int): Player? = dbQuery {
        val insertStatement = Players.insert {
            it[Players.firstName] = firstName
            it[Players.lastName] = lastName
            it[Players.playerNum] = playerNum
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPlayer)
    }

    override suspend fun editPlayer(id: Int, firstName: String, lastName: String, playerNum: Int): Boolean = dbQuery {
        Players.update ({ Players.id eq id }) {
            it[Players.firstName] = firstName
            it[Players.lastName] = lastName
            it[Players.playerNum] = playerNum
        } > 0
    }

    override suspend fun deletePlayer(id: Int): Boolean = dbQuery {
        Players.deleteWhere { Players.id eq id } > 0
    }
}

val playerDao: PlayerDAOFacade = PlayerDAOFacadeImpl().apply {
    runBlocking {
        if(allPlayers().isEmpty()) {
            addNewPlayer("Test", "Player", 99)
        }
    }
}