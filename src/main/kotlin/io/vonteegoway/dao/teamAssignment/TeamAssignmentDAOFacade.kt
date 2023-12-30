package io.vonteegoway.dao.teamAssignment

import io.vonteegoway.models.*

interface TeamAssignmentDAOFacade {
    suspend fun allTeamAssignments(): List<TeamAssignment>
    suspend fun teamAssignmentsByTeam(teamId: Int): List<TeamAssignment>
    suspend fun teamAssignmentsByPlayer(playerId: Int): List<TeamAssignment>
    suspend fun addNewTeamAssignment(teamId: Int, playerId: Int): TeamAssignment?
    suspend fun deleteTeamAssignment(id: Int): Boolean
    suspend fun deleteTeamAssignmentsByPlayer(playerId: Int): Boolean
    suspend fun deleteTeamAssignmentsByTeam(teamId: Int): Boolean
}
