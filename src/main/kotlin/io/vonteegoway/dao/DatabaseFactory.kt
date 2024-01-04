package io.vonteegoway.dao

import io.vonteegoway.models.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
//        val driverClassName = ""
        val jdbcURl = "jdbc:sqlite:C:\\Users\\brian\\programming\\databases\\LineupMaker.db"
        val database = Database.connect(jdbcURl)
        transaction(database) {
            SchemaUtils.create(Teams)
            SchemaUtils.create(Players)
            SchemaUtils.create(TeamAssignments)
//            SchemaUtils.create(Positions)
            SchemaUtils.create(PlayerPreferences)
//            SchemaUtils.create(DisfavoredPositions)
        }
    }

    suspend fun <T> dbQuery(block: suspend() -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}