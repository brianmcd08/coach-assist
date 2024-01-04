package io.vonteegoway.dao.playerPreference

import io.vonteegoway.dao.DatabaseFactory.dbQuery
import io.vonteegoway.models.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PlayerPreferenceDAOFacadeImpl : PlayerPreferenceDAOFacade {
    private fun resultRowToPlayerPreference(row: ResultRow) = PlayerPreference(
        id = row[PlayerPreferences.id],
        playerId = row[PlayerPreferences.playerId],
        teamId = row[PlayerPreferences.teamId],
        position = row[PlayerPreferences.position],
        preference = row[PlayerPreferences.preference]
    )

    override suspend fun allPlayerPreferences(): List<PlayerPreference> =
        dbQuery {
            PlayerPreferences.selectAll().map(::resultRowToPlayerPreference)
        }

    override suspend fun playerPreferencesByPlayer(playerId: Int): List<PlayerPreference> = dbQuery {
        PlayerPreferences
            .select { PlayerPreferences.playerId eq playerId }
            .map(::resultRowToPlayerPreference)
    }

    override suspend fun playerPreferencesByTeam(teamId: Int): List<PlayerPreference> = dbQuery {
        PlayerPreferences
            .select { PlayerPreferences.teamId eq teamId }
            .map(::resultRowToPlayerPreference)
    }

    override suspend fun upsertPreference(playerId: Int, teamId: Int, position: Int, preferred: Int): PlayerPreference? = dbQuery {
        val insertStatement = PlayerPreferences.replace {
            it[PlayerPreferences.playerId] = playerId
            it[PlayerPreferences.teamId] = teamId
            it[PlayerPreferences.position] = position
            it[PlayerPreferences.preference] = preferred
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPlayerPreference)
    }

    override suspend fun removePreference(preferenceId: Int): Boolean = dbQuery {
        PlayerPreferences.deleteWhere { id eq preferenceId } > 0
    }
}

val playerPreferenceDao: PlayerPreferenceDAOFacade = PlayerPreferenceDAOFacadeImpl().apply {runBlocking {}
}