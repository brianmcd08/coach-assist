package io.vonteegoway.dao.teamAssignment

import io.vonteegoway.dao.DatabaseFactory.dbQuery
import io.vonteegoway.models.TeamAssignment
import io.vonteegoway.models.TeamAssignments
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class TeamAssignmentDAOFacadeImpl : TeamAssignmentDAOFacade {
    private fun resultRowToTeamAssignment(row: ResultRow) = TeamAssignment(
        id = row[TeamAssignments.id],
        teamId = row[TeamAssignments.teamId],
        playerId = row[TeamAssignments.playerId]
    )

    override suspend fun allTeamAssignments(): List<TeamAssignment> = dbQuery {
        TeamAssignments.selectAll().map(::resultRowToTeamAssignment)
    }

    override suspend fun teamAssignmentsByTeam(teamId: Int): List<TeamAssignment> = dbQuery {
        TeamAssignments
            .select { TeamAssignments.teamId eq teamId }
            .map(::resultRowToTeamAssignment)
    }

    override suspend fun teamAssignmentsByPlayer(playerId: Int): List<TeamAssignment> = dbQuery {
        TeamAssignments
            .select { TeamAssignments.playerId eq playerId }
            .map(::resultRowToTeamAssignment)
    }

    override suspend fun addNewTeamAssignment(teamId: Int, playerId: Int): TeamAssignment? = dbQuery {
        val insertStatement = TeamAssignments.insert {
            it[TeamAssignments.teamId] = teamId
            it[TeamAssignments.playerId] = playerId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTeamAssignment)
    }

    override suspend fun deleteTeamAssignment(id: Int): Boolean = dbQuery {
        TeamAssignments.deleteWhere { TeamAssignments.id eq id } > 0
    }

    override suspend fun deleteTeamAssignmentsByPlayer(playerId: Int): Boolean = dbQuery {
        TeamAssignments.deleteWhere { TeamAssignments.playerId eq playerId } > 0
    }

    override suspend fun deleteTeamAssignmentsByTeam(teamId: Int): Boolean = dbQuery {
        TeamAssignments.deleteWhere { TeamAssignments.teamId eq teamId } > 0
    }
}

val teamAssignmentDao: TeamAssignmentDAOFacade = TeamAssignmentDAOFacadeImpl().apply {
    runBlocking {
        if(allTeamAssignments().isEmpty()) {
            addNewTeamAssignment(1, 1)
        }
    }
}