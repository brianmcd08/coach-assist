package io.vonteegoway.dao.team

import io.vonteegoway.dao.DatabaseFactory.dbQuery
import io.vonteegoway.models.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class TeamDAOFacadeImpl : TeamDAOFacade {
    private fun resultRowToTeam(row: ResultRow) = Team(
        id = row[Teams.id],
        teamName = row[Teams.teamName]
    )

    override suspend fun allTeams(): List<Team> = dbQuery {
        Teams.selectAll().map(::resultRowToTeam)
    }

    override suspend fun team(id: Int): Team? = dbQuery {
        Teams
            .select { Teams.id eq id }
            .map(::resultRowToTeam)
            .singleOrNull()
    }

    override suspend fun addNewTeam(name: String): Team? = dbQuery {
        val insertStatement = Teams.insert {
            it[teamName] = name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTeam)
    }

    override suspend fun editTeam(id: Int, name: String): Boolean = dbQuery {
        Teams.update({ Teams.id eq id }) {
            it[Teams.teamName] = name
        } > 0
    }

    override suspend fun deleteTeam(id: Int): Boolean = dbQuery {
        Teams.deleteWhere { Teams.id eq id } > 0
    }
}

val teamDao: TeamDAOFacade = TeamDAOFacadeImpl().apply {
    runBlocking {
        if(allTeams().isEmpty()) {
            addNewTeam("Generic Team")
        }
    }
}