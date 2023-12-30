package io.vonteegoway.dao.team

import io.vonteegoway.models.*

interface TeamDAOFacade {
    suspend fun allTeams(): List<Team>
    suspend fun team(id: Int): Team?
    suspend fun addNewTeam(name: String): Team?
    suspend fun editTeam(id: Int, name: String): Boolean
    suspend fun deleteTeam(id: Int): Boolean
}
