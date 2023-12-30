package io.vonteegoway.models

import org.jetbrains.exposed.sql.*

data class Team(val id: Int, val teamName: String)

object Teams : Table("team") {
    val id = integer("id").autoIncrement()
    val teamName = varchar("team_name", 50)

    override val primaryKey = PrimaryKey(id)
}

