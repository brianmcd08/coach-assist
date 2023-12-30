package io.vonteegoway.models

import org.jetbrains.exposed.sql.*

data class Player(val id: Int,
                  val firstName: String,
                  val lastName: String,
                  val playerNum: Int)

object Players : Table("player") {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 32)
    val lastName = varchar("last_name", 32)
    val playerNum = integer("player_num")

    override val primaryKey = PrimaryKey(id)
}

