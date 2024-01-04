package io.vonteegoway.dao.playerPreference

import io.vonteegoway.models.*

interface PlayerPreferenceDAOFacade {
    suspend fun allPlayerPreferences(): List<PlayerPreference>
    suspend fun playerPreferencesByPlayer(playerId: Int): List<PlayerPreference>
    suspend fun playerPreferencesByTeam(teamId: Int): List<PlayerPreference>
    suspend fun upsertPreference(playerId: Int, teamId: Int, position: Int, preferred: Int): PlayerPreference?
    suspend fun removePreference(preferenceId: Int): Boolean
}
