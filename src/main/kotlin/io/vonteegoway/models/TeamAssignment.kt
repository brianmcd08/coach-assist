package io.vonteegoway.models

import org.jetbrains.exposed.sql.*

data class TeamAssignment(val id: Int, val teamId: Int, val playerId: Int)

object TeamAssignments : Table("team_assignment") {
    val id = integer("id").autoIncrement()
    val teamId = integer("team_id").references(Teams.id)
    val playerId = integer("player_id").references(Players.id)

    override val primaryKey = PrimaryKey(id)
}

