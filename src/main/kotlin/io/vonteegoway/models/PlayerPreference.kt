package io.vonteegoway.models

import org.jetbrains.exposed.sql.Table

data class PlayerPreference (val id: Int,
                             val playerId: Int,
                             val teamId: Int,
                             val position: Int,
                             val preference: Int)

object PlayerPreferences : Table("player_preference") {
    val id = integer("id").autoIncrement()
    val playerId = integer("player_id")
    val teamId = integer("team_id")
    val position = integer("position")
    val preference = integer("preference")

    override val primaryKey = PrimaryKey(id)
}